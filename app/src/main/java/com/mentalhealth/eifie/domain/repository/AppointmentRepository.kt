package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.request.AppointmentRequest
import com.mentalhealth.eifie.data.network.models.response.AppointmentRegisterResponse
import com.mentalhealth.eifie.data.network.models.response.AppointmentResponse

interface AppointmentRepository {
    suspend fun getAppointmentsByPatient(patient: Int, startDate: String, endDate: String): DataResult<List<AppointmentResponse>, Exception>
    suspend fun getAppointmentsByPsychologist(psychologist: Int, startDate: String, endDate: String): DataResult<List<AppointmentResponse>, Exception>
    suspend fun saveAppointment(appointment: AppointmentRequest): DataResult<AppointmentRegisterResponse, Exception>
}