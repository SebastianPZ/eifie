package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.AppointmentMapper
import com.mentalhealth.eifie.data.mappers.impl.AppointmentRegisterMapper
import com.mentalhealth.eifie.data.mappers.impl.AppointmentRequestMapper
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentParams
import com.mentalhealth.eifie.domain.entities.Role
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
    private val dataAccess: DataAccess,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppointmentRepository {
    override suspend fun getAppointmentsByPatient(
        patient: Long,
        startDate: String,
        endDate: String
    ): EResult<List<Appointment>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getAppointmentByPatient(token.formatToken(), patient, startDate, endDate)
            },
            { response -> response?.data?.let { AppointmentMapper.mapToEntity(it) }?.sortedWith(
                compareBy({it.date}, {it.time}))}
        )
    }

    override suspend fun getAppointmentsByPsychologist(
        psychologist: Long,
        startDate: String,
        endDate: String
    ): EResult<List<Appointment>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.getAppointmentByPsychologist(token.formatToken(), psychologist, startDate, endDate)
            },
            { response -> response?.data?.let { AppointmentMapper.mapToEntity(it).map { appointment ->
                appointment.apply { type = Role.PSYCHOLOGIST.ordinal }
            } }?.sortedWith(
                compareBy({it.date}, {it.time})) }
        )
    }

    override suspend fun saveAppointment(
        appointment: AppointmentParams
    ): EResult<Appointment, Exception> = withContext(dispatcher){
        dataAccess.performApiCall(
            {
                val token = preferences.readPreference(tokenPreferences) ?: emptyString()
                api.saveAppointment(token.formatToken(), AppointmentRequestMapper.mapFromEntity(appointment))
            },
            { response -> response?.data?.let { AppointmentRegisterMapper.mapToEntity(it) } }
        )
    }
}