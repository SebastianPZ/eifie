package com.mentalhealth.eifie.ui.appointment.register

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.api.models.request.AppointmentRequest
import com.mentalhealth.eifie.domain.entities.states.RegisterResult
import com.mentalhealth.eifie.domain.usecases.ScheduleAppointmentUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.util.ERR_REGISTER
import com.mentalhealth.eifie.util.FormField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentRegisterViewModel @Inject constructor(
    private val scheduleAppointment: ScheduleAppointmentUseCase
): LazyViewModel() {

    private val _validForm: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val validForm = _validForm.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = false
    )

    private val appointment: AppointmentRequest by lazy { AppointmentRequest() }

    fun setFormValue(text: String, field: FormField) {
        when(field) {
            FormField.DATE -> appointment.date = text
            FormField.TIME -> appointment.time = text
            else -> appointment.patientId = 0
        }

        _validForm.value = appointment.isValidRequest()
    }

    fun submitForm() {
        if(appointment.isValidRequest()) {
            registerAppointment()
        }
    }

    private fun registerAppointment() = viewModelScope.launch {
        scheduleAppointment.invoke(appointment)
            .onStart { viewState.value = AppointmentRegisterViewState.Loading }
            .onEach { handleScheduleAppointmentResult(it) }
            .catch { viewState.value = AppointmentRegisterViewState.Error(ERR_REGISTER) }
            .launchIn(viewModelScope)
    }

    private fun handleScheduleAppointmentResult(result: RegisterResult) {
        when(result) {
            is RegisterResult.Error -> result.run {
                viewState.value = AppointmentRegisterViewState.Error(message)
            }
            RegisterResult.Success -> viewState.value = AppointmentRegisterViewState.Success
            RegisterResult.Loading -> viewState.value = AppointmentRegisterViewState.Loading
        }
    }

}