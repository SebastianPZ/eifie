package com.mentalhealth.eifie.ui.register.psychologist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.response.PatientResponse
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.domain.entities.states.CodeState
import com.mentalhealth.eifie.domain.usecases.AssignPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.ValidatePsychologistCodeUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.register.Step
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel(assistedFactory = RegisterPsychologistViewModel.RegisterPsychologistViewModelFactory::class)
class RegisterPsychologistViewModel @AssistedInject constructor(
    @Assisted val patientId: Long,
    private val validateCodeUseCase: ValidatePsychologistCodeUseCase,
    private val assignPsychologistUseCase: AssignPsychologistUseCase
): LazyViewModel() {

    private val steps = listOf(
        Step(order = Step.FIRST, title = "Validación código"),
        Step(order = Step.SECOND, title = "Validación psicólogo")
    )

    private val _codeError: MutableStateFlow<String> = MutableStateFlow("")
    val codeError = _codeError.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = ""
    )

    private val _actualStep: MutableStateFlow<Step> = MutableStateFlow(steps.first())
    val actualStep = _actualStep.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = steps.first()
    )

    val stepsSize get() = steps.size

    fun updateStep(step: Int) {
        _actualStep.value = steps.first { it.order == step }
    }

    fun validateCode(code: String, onSuccess: (psychologist: Psychologist) -> Unit = {}) {
        validatePsychologistCode(code, onSuccess)
    }

    fun assignPsychologist(psychologistId: Long, onResult: (DataResult<PatientResponse, Exception>) -> Unit = {}) {
        assignToPsychologist(psychologistId, onResult)
    }

    private fun validatePsychologistCode(code: String, onSuccess: (psychologist: Psychologist) -> Unit) = viewModelScope.launch {
        validateCodeUseCase.invoke(code)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                viewState.value = RegisterPsychologistViewState.Loading
            }.onEach {
                when(it) {
                    is CodeState.Error -> {
                        _codeError.value = it.error
                        viewState.value = RegisterPsychologistViewState.Idle
                    }
                    is CodeState.Success -> {
                        onSuccess(it.psychologist)
                        viewState.value = RegisterPsychologistViewState.Idle
                    }
                    else -> viewState.value = RegisterPsychologistViewState.Loading
                }
            }.catch {
                Log.e("RegisterViewModel", "Error", it)
            }.launchIn(viewModelScope)
    }


    private fun assignToPsychologist(psychologistId: Long, onResult: (DataResult<PatientResponse, Exception>) -> Unit) = viewModelScope.launch {
        assignPsychologistUseCase.invoke(patientId, psychologistId)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }}
            .onStart { viewState.value = RegisterPsychologistViewState.Loading }
            .onEach { onResult(it) }
            .catch {  }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    fun interface RegisterPsychologistViewModelFactory {
        fun create(patientId: Long): RegisterPsychologistViewModel
    }
}