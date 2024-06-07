package com.mentalhealth.eifie.domain.entities

class AppointmentParams {
    var patientId: Long? = null
    var psychologistId: Long? = null
    var reason: String = ""
    var date: String = ""
    var time: String = ""
    fun isValid(): Boolean = date.isNotBlank() && time.isNotBlank() && patientId != null
}