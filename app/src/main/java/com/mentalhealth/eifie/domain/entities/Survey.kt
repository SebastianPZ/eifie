package com.mentalhealth.eifie.domain.entities

class Survey(
    val title: String,
    val description: String,
    val questions: Int,
    val minimumTime: Int,
    val maxTime: Int
)