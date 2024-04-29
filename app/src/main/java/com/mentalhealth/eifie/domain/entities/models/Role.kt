package com.mentalhealth.eifie.domain.entities.models

import com.mentalhealth.eifie.data.api.models.response.LoginResponse
import com.mentalhealth.eifie.data.database.entities.User

enum class Role(val text: String, val abb: String) {
    PATIENT("Paciente", "PA"),
    PSYCHOLOGIST("Psicólogo", "PS")
}

fun User.getRole(): Role {
    return when(this.role) {
        1 -> Role.PSYCHOLOGIST
        else -> Role.PATIENT
    }
}

fun LoginResponse.getRole(): Role {
    return if (this.patientId != null) Role.PATIENT
    else Role.PSYCHOLOGIST
}