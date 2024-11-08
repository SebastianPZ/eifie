package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.PatientMapper
import com.mentalhealth.eifie.data.mappers.impl.UserPatientMapper
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.repository.PatientRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
import com.mentalhealth.eifie.util.userRolePreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PatientDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val dataAccess: DataAccess,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PatientRepository {
    override suspend fun getPatientById(patientId: Long): EResult<Patient, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getPatientById(token.formatToken(), patientId)
            },
            { response -> response?.data?.let { UserPatientMapper.mapToEntity(it) } }
        )
    }

    override suspend fun searchPatientBy(lastname: String): EResult<List<Patient>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                val psychologist = preferences.readPreference(userRolePreferences) ?: 0
                api.searchPatient(token.formatToken(), lastname, psychologist)
            },
            { response -> response?.data?.map { PatientMapper.mapToEntity(it) } }
        )
    }
}