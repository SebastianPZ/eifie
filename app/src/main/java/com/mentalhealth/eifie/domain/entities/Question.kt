package com.mentalhealth.eifie.domain.entities

data class Question(
    val id: Long,
    val text: String,
    val options: List<QuestionOption>
)
