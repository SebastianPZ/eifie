package com.mentalhealth.eifie.domain.entities

data class Form(
    val id: Int,
    val name: String,
    val description: String,
    val questions: String,
    val time: String,
    val timeUnit: String = "min"
)