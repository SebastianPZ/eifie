package com.mentalhealth.eifie.ui.appointment

import com.mentalhealth.eifie.domain.entities.models.UserAppointment
import com.mentalhealth.eifie.ui.common.ViewState

sealed class AppointmentViewState: ViewState() {
    object Idle: AppointmentViewState()
    object Loading: AppointmentViewState()
    data class Success(val appointments: UserAppointment): AppointmentViewState()
    data class Error(val message: String): AppointmentViewState()
}