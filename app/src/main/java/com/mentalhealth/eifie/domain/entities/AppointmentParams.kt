package com.mentalhealth.eifie.domain.entities

class AppointmentParams {
    var patientId: Int? = null
    var psychologistId: Int? = null
    var date: String = ""
    var time: String = ""
    fun isValid(): Boolean = date.isNotBlank() && time.isNotBlank() && patientId != null
}