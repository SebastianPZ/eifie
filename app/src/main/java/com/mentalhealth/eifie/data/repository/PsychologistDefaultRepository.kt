package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.PatientMapper
import com.mentalhealth.eifie.data.mappers.impl.UserPsychologistMapper
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PsychologistDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val dataAccess: DataAccess,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PsychologistRepository {
    override suspend fun getPsychologistById(psychologistId: Long): EResult<Psychologist, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getPsychologistById(token.formatToken(), psychologistId)
            },
            { response -> response?.data?.let { UserPsychologistMapper.mapToEntity(it) }
            }
        )
    }

    override suspend fun listPatients(psychologistId: Long): EResult<List<Patient>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.listPatientByPsychologist(token.formatToken(), psychologistId)
            },
            { response -> response?.data?.map {
                PatientMapper.mapToEntity(it) }
            }
        )
    }
}