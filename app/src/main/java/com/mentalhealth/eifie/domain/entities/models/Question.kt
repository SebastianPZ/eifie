package com.mentalhealth.eifie.domain.entities.models

data class Question(
    val id: Int,
    val text: String,
    val options: List<QuestionOption>
)
