package com.mentalhealth.eifie.ui.appointment.register

import com.mentalhealth.eifie.ui.common.ViewState

sealed class AppointmentRegisterViewState: ViewState() {
    object Idle: AppointmentRegisterViewState()
    object Loading: AppointmentRegisterViewState()
    object Success: AppointmentRegisterViewState()
    data class Error(val message: String): AppointmentRegisterViewState()
}