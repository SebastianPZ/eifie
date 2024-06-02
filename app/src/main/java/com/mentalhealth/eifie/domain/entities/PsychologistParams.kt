package com.mentalhealth.eifie.domain.entities

data class PsychologistParams(
    val birthDate: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val hospitalId: Int = 0
)