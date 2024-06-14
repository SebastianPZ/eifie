package com.mentalhealth.eifie.ui.view.appointment

import com.mentalhealth.eifie.domain.entities.Appointment

sealed class AppointmentsState {
    object Idle: AppointmentsState()
    object Loading: AppointmentsState()
    object Success: AppointmentsState()
    object Error: AppointmentsState()
}