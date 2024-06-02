package com.mentalhealth.eifie.data.models.response

import com.mentalhealth.eifie.domain.entities.Appointment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AppointmentResponse(
    val appointmentId: Int? = null,
    val date: String? = null,
    val hospital: String? = null,
    val patientId: String? = null,
    val patientFirstName: String? = null,
    val patientLastName: String? = null,
    val psychologistId: Int? = null,
    val psychologistFirstName: String? = null,
    val psychologistLastName: String? = null,
    val status: String? = null,
    val time: String? = null
)


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

data class AppointmentRegisterResponse(
    val appointmentId: Int? = null,
    val date: String? = null,
    val hospital: String? = null,
    val patient: UserPatientResponse? = null,
    val psychologist: UserPsychologistResponse? = null,
    val status: String? = null,
    val time: String? = null
)