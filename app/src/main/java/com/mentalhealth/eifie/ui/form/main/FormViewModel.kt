package com.mentalhealth.eifie.ui.form.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.models.Question
import com.mentalhealth.eifie.domain.usecases.GetFormDataUseCase
import com.mentalhealth.eifie.domain.usecases.GetFormQuestionsUseCase
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

@HiltViewModel(assistedFactory = FormViewModel.FormViewModelFactory::class)
class FormViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val getFormDataUseCase: GetFormDataUseCase,
    private val getFormQuestionsUseCase: GetFormQuestionsUseCase
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

    private val _formName: MutableStateFlow<String> = MutableStateFlow("")

    val formName = _formName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ""
    )

    init {
        initFormData()
    }

    fun initFormQuestions() = viewModelScope.launch {
        getFormQuestions().await()?.let {
            _formQuestions.value = it
            _formName.value = it.first().text
        }
    }

    fun saveQuestionAnswer(questionId: Int) {
        //save question answer
    }

    fun goToNextQuestion(questionId: Int, onComplete: () -> Unit = {}) {
        //save question answer
        when {
            questionId == formQuestions.value.lastIndex - 1 -> {
                _formState.value = FormState.LastQuestion
                _formName.value = _formQuestions.value[questionId + 1].text
            }
            questionId >= formQuestions.value.lastIndex -> registerQuestionsAnswers()
            else -> _formName.value = _formQuestions.value[questionId + 1].text
        }
        onComplete()
    }

    private fun initFormData() = viewModelScope.launch {
        getFormDataUseCase.invoke(id)
            .onStart { _formState.value = FormState.Idle }
            .onEach { _formName.value = it.name }
            .catch { _formState.value = FormState.Error }
            .launchIn(viewModelScope)
    }

    private fun getFormQuestions() = viewModelScope.async {
        getFormQuestionsUseCase.invoke(id)
            .onStart { _formState.value = FormState.InProgress }
            .catch { _formState.value = FormState.Error }
            .firstOrNull()
    }

    private fun registerQuestionsAnswers() {
        //register questions answers
    }


    @AssistedFactory
    fun interface FormViewModelFactory {
        fun create(id: Int): FormViewModel
    }

}