package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.response.AppointmentResponse

interface AppointmentRepository {
    suspend fun getAppointmentsByPatient(patient: Int, startDate: String, endDate: String): DataResult<List<AppointmentResponse>, Exception>
    suspend fun getAppointmentsByPsychologist(): DataResult<List<AppointmentResponse>, Exception>
}