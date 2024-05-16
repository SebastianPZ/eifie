package com.mentalhealth.eifie.data.network.models.request

data class PsychologistRequest(
    val birthDate: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val hospitalId: Int = 0
)
