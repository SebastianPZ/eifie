package com.mentalhealth.eifie.domain.entities.models

import com.mentalhealth.eifie.data.network.models.response.AppointmentResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Appointment(
    val appointmentId: Int = 0,
    val patientFirstName: String = "",
    val patientLastName: String = "",
    val psychologistFirstName: String = "",
    val psychologistLastName: String = "",
    val status: String = "",
    val date: Date = Date(),
    val time: String = "",
    val type: Int = Role.PATIENT.ordinal
) {
    val patientName = "$patientLastName, $patientFirstName"
    val psychologistName = "$psychologistLastName, $psychologistFirstName"
}

fun AppointmentResponse.toDomain(role: Int): Appointment {
    return Appointment(
        appointmentId = this.appointmentId ?: 0,
        patientFirstName = this.patientFirstName ?: "",
        patientLastName = this.patientLastName ?: "",
        psychologistFirstName = this.psychologistFirstName ?: "",
        psychologistLastName = this.psychologistLastName ?: "",
        status = this.status ?: "",
        date = SimpleDateFormat("yyyy-MM-dd", Locale("es", "PE")).parse(this.date ?: "") ?: Date(),
        time = this.time ?: "",
        type = role
    )
}