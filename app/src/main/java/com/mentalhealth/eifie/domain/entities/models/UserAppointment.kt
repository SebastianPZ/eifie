package com.mentalhealth.eifie.domain.entities.models

import com.mentalhealth.eifie.data.api.models.response.AppointmentResponse

data class UserAppointment(
    val todayList: List<AppointmentResponse> = emptyList(),
    val weekList: List<AppointmentResponse> = emptyList(),
    val soonList: List<AppointmentResponse> = emptyList(),
    val isEmpty: Boolean = todayList.isEmpty() && weekList.isEmpty() && soonList.isEmpty()
)
