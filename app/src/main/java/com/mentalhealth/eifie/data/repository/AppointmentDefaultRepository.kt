package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.api.ApiService
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.response.AppointmentResponse
import com.mentalhealth.eifie.data.api.performApiCall
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.data.preferences.EPreferences
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppointmentDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppointmentRepository {
    override suspend fun getAppointmentsByPatient(
        patient: Int,
        startDate: String,
        endDate: String
    ): DataResult<List<AppointmentResponse>, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getAppointmentByPatient(token.formatToken(), patient, startDate, endDate)
            },
            { response -> response?.data }
        )
    }

    override suspend fun getAppointmentsByPsychologist(): DataResult<List<AppointmentResponse>, Exception> {
        TODO("Not yet implemented")
    }
}