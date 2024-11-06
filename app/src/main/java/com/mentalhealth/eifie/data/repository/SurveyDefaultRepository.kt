package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.AnswerMapper
import com.mentalhealth.eifie.data.mappers.impl.QuestionMapper
import com.mentalhealth.eifie.data.models.request.AnswerRequest
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.Answer
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Question
import com.mentalhealth.eifie.domain.repository.SurveyRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SurveyDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val dataAccess: DataAccess,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): SurveyRepository {
    override suspend fun getQuestions(): EResult<List<Question>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.surveyQuestions(token.formatToken())
            },
            { response -> response?.data?.map { QuestionMapper.mapToEntity(it) } }
        )
    }

    override suspend fun sendAnswers(patientId: Long, answers: List<Answer>): EResult<String, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.surveyAnswers(token.formatToken(), AnswerRequest(patientId, answers.map { AnswerMapper.mapFromEntity(it) }))
            },
            { _ -> "Success" }
        )
    }

    override suspend fun validateSurvey(patientId: Long): EResult<String, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.surveyValidate(token.formatToken(), patientId)
            },
            { _ -> "Validate" }
        )
    }
}