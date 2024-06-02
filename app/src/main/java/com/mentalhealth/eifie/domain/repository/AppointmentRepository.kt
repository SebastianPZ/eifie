package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentParams

interface AppointmentRepository {
    suspend fun getAppointmentsByPatient(patient: Long, startDate: String, endDate: String): EResult<List<Appointment>, Exception>
    suspend fun getAppointmentsByPsychologist(psychologist: Long, startDate: String, endDate: String): EResult<List<Appointment>, Exception>
    suspend fun saveAppointment(appointment: AppointmentParams): EResult<Appointment, Exception>
}