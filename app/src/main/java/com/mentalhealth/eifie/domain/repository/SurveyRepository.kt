package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.Answer
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Question

interface SurveyRepository {
    suspend fun getQuestions(): EResult<List<Question>, Exception>
    suspend fun sendAnswers(patientId: Long, answers: List<Answer>): EResult<String, Exception>
    suspend fun validateSurvey(patientId: Long): EResult<String, Exception>
}