package com.mentalhealth.eifie.domain.entities

data class PatientParams(
    val birthDate: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val psychologistId: Int = 0,
    val hospitalId: Int = 0
)