package com.mentalhealth.eifie.ui.view.appointment.register

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.AppointmentParams
import com.mentalhealth.eifie.domain.usecases.ScheduleAppointmentUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
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

    private val appointment: AppointmentParams by lazy { AppointmentParams() }

    fun setFormValue(text: String, field: FormField) {
        when(field) {
            FormField.DATE -> appointment.date = text
            FormField.TIME -> appointment.time = text
            FormField.REASON -> appointment.reason = text
            else -> appointment.patientId = text.toLong()
        }
        _validForm.value = appointment.isValid()
    }

    fun submitForm() {
        if(appointment.isValid()) {
            registerAppointment()
        }
    }

    private fun registerAppointment() = viewModelScope.launch {
        scheduleAppointment.invoke(appointment)
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> it.run {
                        viewState.value = ViewState.Error(error.message ?: ERR_REGISTER)
                    }
                    is EResult.Success -> viewState.value = ViewState.Success
                }
            }
            .catch { viewState.value = ViewState.Error(ERR_REGISTER) }
            .launchIn(viewModelScope)
    }

}