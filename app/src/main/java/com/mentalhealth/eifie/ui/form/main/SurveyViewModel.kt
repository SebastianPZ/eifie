package com.mentalhealth.eifie.ui.form.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.Answer
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Question
import com.mentalhealth.eifie.domain.entities.Survey
import com.mentalhealth.eifie.domain.usecases.GetFormDataUseCase
import com.mentalhealth.eifie.domain.usecases.GetSurveyQuestionsUseCase
import com.mentalhealth.eifie.domain.usecases.SendSurveyAnswersUseCase
import com.mentalhealth.eifie.domain.usecases.ValidateSurveyUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SurveyViewModel.FormViewModelFactory::class)
class SurveyViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val getFormDataUseCase: GetFormDataUseCase,
    private val getSurveyQuestionsUseCase: GetSurveyQuestionsUseCase,
    private val sendSurveyAnswersUseCase: SendSurveyAnswersUseCase,
    private val validateSurveyUseCase: ValidateSurveyUseCase
): ViewModel() {

    private val _formState: MutableStateFlow<FormState> = MutableStateFlow(FormState.Idle)

    val formState = _formState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = FormState.Idle
    )

    private val _formQuestions: MutableStateFlow<List<Question>> = MutableStateFlow(listOf())

    val formQuestions = _formQuestions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _formName: MutableStateFlow<String> = MutableStateFlow("Semanal")

    val formName = _formName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = "Semanal"
    )

    private val _survey: MutableStateFlow<Survey?> = MutableStateFlow(null)

    val survey = _survey.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = null
    )

    private val answers: MutableList<Answer> = mutableListOf()
    private lateinit var answer: Answer

    init {
        initFormData()
        validateSurvey()
    }

    fun initFormState() {
        _formState.value = FormState.Idle
    }

    private fun initFormData() = viewModelScope.launch {
        getFormDataUseCase.invoke(id)
            .onStart { _formState.value = FormState.Loading }
            .onEach {
                _formName.value = it.title
                _survey.value = it
            }
            .catch { _formState.value = FormState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    private fun validateSurvey() = viewModelScope.launch {
        validateSurveyUseCase.invoke()
            .onStart { _formState.value = FormState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> _formState.value = FormState.Done(it.error.message ?: "")
                    is EResult.Success -> _formState.value = FormState.Idle
                }
            }
            .catch { _formState.value = FormState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    private fun getFormQuestions() = viewModelScope.async {
        getSurveyQuestionsUseCase.invoke()
            .onStart { _formState.value = FormState.Loading }
            .catch { _formState.value = FormState.Error(it.message ?: "") }
            .firstOrNull()
    }

    fun initFormQuestions() = viewModelScope.launch {
        getFormQuestions().await()?.let {
            when(it) {
                is EResult.Error -> it.run {
                    Log.e("ERROR", error.message, error)
                }
                is EResult.Success -> it.run {
                    _formState.value = if(data.size >= 2) FormState.InProgress
                    else FormState.LastQuestion

                    _formQuestions.value = data
                    _formName.value = data.first().text
                }
            }
        } ?: throw IllegalArgumentException("ERROR DE PARSEO")
    }

    fun saveQuestionAnswer(questionId: Int, score: Int) {
        answer = Answer(questionId, score)
    }

    fun goToNextQuestion(questionId: Int, onComplete: () -> Unit = {}) {
        answers.add(answer)
        when {
            questionId == formQuestions.value.lastIndex - 1 -> {
                _formState.value = FormState.LastQuestion
                _formName.value = _formQuestions.value[questionId + 1].text
                onComplete()
            }
            questionId >= formQuestions.value.lastIndex -> registerQuestionsAnswers(onComplete)
            else -> {
                _formName.value = _formQuestions.value[questionId + 1].text
                onComplete()
            }
        }
    }

    fun registerQuestionsAnswers(onComplete: () -> Unit = {}) = viewModelScope.launch {
        sendSurveyAnswersUseCase.invoke(answers)
            .onStart { _formState.value = FormState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> _formState.value = FormState.Error(it.error.message ?: "")
                    is EResult.Success -> _formState.value = FormState.Success(it.data)
                }
            }
            .catch {
                _formState.value = FormState.Error(it.message ?: "")
                onComplete()
            }
            .launchIn(viewModelScope)
    }


    @AssistedFactory
    fun interface FormViewModelFactory {
        fun create(id: Int): SurveyViewModel
    }

}