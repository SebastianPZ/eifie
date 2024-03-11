package com.mentalhealth.eifie.data.api.models.response

data class AppointmentResponse(
    val appointmentId: Int? = null,
    val date: String? = null,
    val hospital: String? = null,
    val patient: PatientResponse? = null,
    val psychologist: PsychologistResponse? = null,
    val status: String? = null,
    val time: String? = null
)