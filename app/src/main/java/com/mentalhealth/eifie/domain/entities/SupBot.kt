package com.mentalhealth.eifie.domain.entities

data class SupBot(
    val id: Long? = null,
    val name: String,
    val config: String,
    val photo: String? = null,
)