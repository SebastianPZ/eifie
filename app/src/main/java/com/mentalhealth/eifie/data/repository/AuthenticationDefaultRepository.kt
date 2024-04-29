package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.api.ApiService
import com.mentalhealth.eifie.data.api.models.request.LoginRequest
import com.mentalhealth.eifie.data.api.models.response.PatientResponse
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.performApiCall
import com.mentalhealth.eifie.data.api.models.request.PatientRequest
import com.mentalhealth.eifie.data.api.models.request.PsychologistRequest
import com.mentalhealth.eifie.data.api.models.response.LoginResponse
import com.mentalhealth.eifie.data.api.models.response.PsychologistResponse
import com.mentalhealth.eifie.data.preferences.EPreferences
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.util.TOKEN_KEY
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.tokenPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): AuthenticationRepository {

    override suspend fun authenticateUser(
        request: LoginRequest
    ): DataResult<LoginResponse, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.loginUser(request) },
            { response -> response?.data?.profile },
            { headers -> preferences.savePreference(tokenPreferences, headers[TOKEN_KEY] ?: emptyString()) }
        )
    }

    override suspend fun registerPatient(
        request: PatientRequest
    ): DataResult<PatientResponse, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.registerPatient(request) },
            { response -> response?.data }
        )
    }

    override suspend fun registerPsychologist(request: PsychologistRequest): DataResult<PsychologistResponse, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.registerPsychologist(request) },
            { response -> response?.data }
        )
    }

    override suspend fun validatePsychologistCode(accessCode: String): DataResult<PsychologistResponse, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.validatePsychologistCode(accessCode) },
            { response -> response?.data }
        )
    }

    override suspend fun assignPsychologist(
        patientId: Long,
        psychologistId: Long
    ): DataResult<PatientResponse, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.assignPsychologist(patientId, psychologistId) },
            { response -> response?.data }
        )
    }

}