package com.mentalhealth.eifie.domain.entities.models

sealed class AppointmentListState {
    object Idle: AppointmentListState()
    object Loading: AppointmentListState()
    data class Error(val message: String): AppointmentListState()
    data class Success(val appointments: UserAppointment): AppointmentListState()
}