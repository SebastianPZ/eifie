package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.api.ApiService
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.request.AppointmentRequest
import com.mentalhealth.eifie.data.api.models.response.AppointmentRegisterResponse
import com.mentalhealth.eifie.data.api.models.response.AppointmentResponse
import com.mentalhealth.eifie.data.api.performApiCall
import com.mentalhealth.eifie.data.preferences.EPreferences
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
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

    override suspend fun getAppointmentsByPsychologist(
        psychologist: Int,
        startDate: String,
        endDate: String
    ): DataResult<List<AppointmentResponse>, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getAppointmentByPsychologist(token.formatToken(), psychologist, startDate, endDate)
            },
            { response -> response?.data }
        )
    }

    override suspend fun saveAppointment(
        appointment: AppointmentRequest
    ): DataResult<AppointmentRegisterResponse, Exception> = withContext(dispatcher){
        performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.saveAppointment(token.formatToken(), appointment)
            },
            { response -> response?.data }
        )
    }
}