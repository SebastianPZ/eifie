package com.mentalhealth.eifie.data.api.models.response

data class PatientResponse(
    val patientId: Int? = null,
    val psychologistId: Int? = null,
    val user: UserResponse? = null
)