package com.mentalhealth.eifie.data.models.request

class AppointmentRequest(
    var patientId: Int? = null,
    var psychologistId: Int? = null,
    var date: String = "",
    var time: String = ""
) {
    fun isValidRequest(): Boolean {
        return date.isNotBlank() && time.isNotBlank() && patientId != null
    }
}
