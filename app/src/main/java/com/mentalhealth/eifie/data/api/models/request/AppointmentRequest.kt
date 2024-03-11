package com.mentalhealth.eifie.data.api.models.request

data class AppointmentRequest(
    val patientId: Int,
    val psychologistId: Int,
    val date: String,
    val time: String
)
