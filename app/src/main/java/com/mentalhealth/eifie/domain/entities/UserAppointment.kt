package com.mentalhealth.eifie.domain.entities

import androidx.compose.ui.graphics.Color

data class UserAppointment(
    val appointments: List<AppointmentList> = emptyList(),
    val isEmpty: Boolean = appointments.all { it.list.isEmpty() }
)

data class AppointmentList(
    val type: Int = TODAY,
    val list: List<Appointment> = emptyList(),
    val style: AppointmentStyle
) {
    companion object AppointmentListType {
        const val TODAY = 1
        const val WEEK = 2
        const val SOON = 3
    }
}

class AppointmentStyle(
    val title: String,
    val textColor: Color,
    val containerColor: Color
)