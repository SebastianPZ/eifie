package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.AppointmentRegisterResponse
import com.mentalhealth.eifie.domain.entities.Appointment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppointmentRegisterMapper: Mapper<AppointmentRegisterResponse, Appointment> {
    override fun mapFromEntity(entity: Appointment): AppointmentRegisterResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: AppointmentRegisterResponse): Appointment {
        return Appointment(
            appointmentId = model.appointmentId ?: 0,
            patientFirstName = model.patient?.user?.firstName ?: "",
            patientLastName = model.patient?.user?.lastName ?: "",
            psychologistFirstName = model.psychologist?.user?.firstName ?: "",
            psychologistLastName = model.psychologist?.user?.lastName ?: "",
            status = model.status ?: "",
            date = SimpleDateFormat("yyyy-MM-dd", Locale("es", "PE")).parse(model.date ?: "") ?: Date(),
            time = model.time ?: ""
        )
    }
}