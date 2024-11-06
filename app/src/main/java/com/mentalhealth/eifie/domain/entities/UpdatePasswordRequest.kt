package com.mentalhealth.eifie.domain.entities

data class UpdatePasswordRequest(
    val email: String,
    val actualPassword: String,
    val newPassword: String
)