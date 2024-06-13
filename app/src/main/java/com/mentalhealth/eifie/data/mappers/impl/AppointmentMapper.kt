package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.AppointmentResponse
import com.mentalhealth.eifie.domain.entities.Appointment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppointmentMapper: Mapper<List<AppointmentResponse>, List<Appointment>> {
    override fun mapFromEntity(entity: List<Appointment>): List<AppointmentResponse> {
        return entity.map { appointment ->
            AppointmentResponse(
                appointmentId = appointment.appointmentId,
                patientFirstName = appointment.patientFirstName,
                patientLastName = appointment.patientLastName,
                psychologistFirstName = appointment.psychologistFirstName,
                psychologistLastName = appointment.psychologistLastName,
                status = appointment.status,
                date = SimpleDateFormat("yyyy-MM-dd", Locale("es", "PE")).format(appointment.date),
                time = appointment.time
            )
        }
    }

    override fun mapToEntity(model: List<AppointmentResponse>): List<Appointment> {
        return model.map { appointment ->
            Appointment(
                appointmentId = appointment.appointmentId ?: 0,
                patientFirstName = appointment.patientFirstName ?: "",
                patientLastName = appointment.patientLastName ?: "",
                psychologistFirstName = appointment.psychologistFirstName ?: "",
                psychologistLastName = appointment.psychologistLastName ?: "",
                status = appointment.status ?: "",
                date = SimpleDateFormat("yyyy-MM-dd", Locale("es", "PE")).parse(appointment.date ?: "") ?: Date(),
                time = appointment.time ?: ""
            )
        }
    }
}