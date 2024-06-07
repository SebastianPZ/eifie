package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Question

interface SurveyRepository {
    suspend fun getQuestions(): EResult<List<Question>, Exception>
    suspend fun sendAnswers(): EResult<List<Question>, Exception>
}