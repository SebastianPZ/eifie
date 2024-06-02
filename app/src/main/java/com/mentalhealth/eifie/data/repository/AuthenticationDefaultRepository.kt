package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.models.request.LoginRequest
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.UserPatientMapper
import com.mentalhealth.eifie.data.mappers.impl.PatientRequestMapper
import com.mentalhealth.eifie.data.mappers.impl.UserPsychologistMapper
import com.mentalhealth.eifie.data.mappers.impl.PsychologistRequestMapper
import com.mentalhealth.eifie.data.mappers.impl.LoginMapper
import com.mentalhealth.eifie.domain.entities.PatientParams
import com.mentalhealth.eifie.domain.entities.PsychologistParams
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.util.TOKEN_KEY
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
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
        email: String,
        password: String
    ): EResult<User, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.loginUser(LoginRequest(email = email, password = password)) },
            { response -> response?.data?.profile?.let { LoginMapper.mapToEntity(it) } },
            { headers -> preferences.savePreference(tokenPreferences, headers[TOKEN_KEY] ?: emptyString()) }
        )
    }

    override suspend fun registerPatient(
        request: PatientParams
    ): EResult<Patient, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.registerPatient(PatientRequestMapper.mapFromEntity(request)) },
            { response -> response?.data?.let { UserPatientMapper.mapToEntity(it) } }
        )
    }

    override suspend fun registerPsychologist(request: PsychologistParams): EResult<Psychologist, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.registerPsychologist(PsychologistRequestMapper.mapFromEntity(request)) },
            { response -> response?.data?.let { UserPsychologistMapper.mapToEntity(it) } }
        )
    }

    override suspend fun generatePsychologistCode(psychologistId: Long): EResult<String, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.generatePsychologistCode(token.formatToken(), psychologistId)
            },
            { response -> response?.data }
        )
    }

    override suspend fun validatePsychologistCode(accessCode: String): EResult<Psychologist, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.validatePsychologistCode(token.formatToken(), accessCode)
            },
            { response -> response?.data?.let { UserPsychologistMapper.mapToEntity(it) } }
        )
    }

    override suspend fun assignPsychologist(
        patientId: Long,
        psychologistId: Long
    ): EResult<Patient, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.assignPsychologist(patientId, psychologistId) },
            { response -> response?.data?.let { UserPatientMapper.mapToEntity(it) } }
        )
    }

}