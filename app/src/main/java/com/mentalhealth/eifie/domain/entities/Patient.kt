package com.mentalhealth.eifie.domain.entities

data class Patient(
    val id: Long = 0,
    val psychologistAssigned: Long = 0,
    val firstname: String = "",
    val lastname: String = "",
    val username: String = "",
    val birthDate: String = "",
    val email: String = "",
    val state: String = "",
    val picture: String? = null
)