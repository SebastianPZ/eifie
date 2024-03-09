package com.mentalhealth.eifie.data.api.models.request

data class PatientRequest(
    val birthDate: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val psychologistId: Int = 0
)
