package com.mentalhealth.eifie.domain.entities

import java.util.Date

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
