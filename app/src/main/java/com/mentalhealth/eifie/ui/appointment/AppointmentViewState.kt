package com.mentalhealth.eifie.ui.appointment

import com.mentalhealth.eifie.ui.common.ViewState

sealed class AppointmentViewState: ViewState() {
    object Idle: AppointmentViewState()
    object Loading: AppointmentViewState()
    object Success: AppointmentViewState()
    data class Error(val message: String): AppointmentViewState()
}