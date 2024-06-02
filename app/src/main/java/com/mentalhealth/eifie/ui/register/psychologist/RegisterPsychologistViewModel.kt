package com.mentalhealth.eifie.ui.register.psychologist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.domain.usecases.AssignPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.ValidateAssignCodeUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.util.ERR_ACCESS_CODE
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
    private val validateCodeUseCase: ValidateAssignCodeUseCase,
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

    private val _psychologist: MutableStateFlow<Psychologist> = MutableStateFlow(Psychologist())
    val psychologist = _psychologist.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = Psychologist()
    )

    val stepsSize get() = steps.size

    fun updateStep(step: Int) {
        _actualStep.value = steps.first { it.order == step }
    }

    fun validateCode(code: String, onSuccess: () -> Unit = {}) {
        validatePsychologistCode(code, onSuccess)
    }

    fun assignPsychologist(onSuccess: () -> Unit = {}) {
        assignToPsychologist(onSuccess)
    }

    private fun validatePsychologistCode(code: String, onSuccess: () -> Unit) = viewModelScope.launch {
        validateCodeUseCase.invoke(code)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                viewState.value = RegisterPsychologistViewState.Loading
            }.onEach {
                when(it) {
                    is EResult.Error -> {
                        _codeError.value = it.error.message ?: ERR_ACCESS_CODE
                        viewState.value = RegisterPsychologistViewState.Idle
                    }
                    is EResult.Success -> it.run {
                        _psychologist.value = data
                        onSuccess()
                        viewState.value = RegisterPsychologistViewState.Idle
                    }
                }
            }.catch {
                Log.e("RegisterViewModel", "Error", it)
            }.launchIn(viewModelScope)
    }


    private fun assignToPsychologist(onSuccess: () -> Unit = {}) = viewModelScope.launch {
        assignPsychologistUseCase.invoke(patientId, _psychologist.value.id)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }}
            .onStart { viewState.value = RegisterPsychologistViewState.Loading }
            .onEach { onSuccess() }
            .catch {  }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    fun interface RegisterPsychologistViewModelFactory {
        fun create(patientId: Long): RegisterPsychologistViewModel
    }
}