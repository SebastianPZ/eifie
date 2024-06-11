package com.mentalhealth.eifie.data.models.request

class AnswerRequest(
    val patientId: Long,
    val questions: List<AnswerScore>
)

class AnswerScore(
    val questionId: Int,
    val score: Int
)