package com.mentalhealth.eifie.data.models.request

class AppointmentRequest(
    var patientId: Long? = null,
    var psychologistId: Long? = null,
    var date: String = "",
    var time: String = ""
)
