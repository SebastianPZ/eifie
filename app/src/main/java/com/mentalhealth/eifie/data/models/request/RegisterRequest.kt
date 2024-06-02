package com.mentalhealth.eifie.data.models.request

import com.mentalhealth.eifie.domain.entities.PatientParams
import com.mentalhealth.eifie.domain.entities.PsychologistParams

data class RegisterRequest(
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val email: String = "",
    val password: String = "",
    val hospital: Int? = null
) {

    fun toPsychologistRequest(): PsychologistParams {
        return PsychologistParams(
            birthDate = birthDate,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            hospitalId = hospital ?: 0
        )
    }

    fun toPatientRequest(): PatientParams {
        return PatientParams(
            birthDate = birthDate,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
    }

    override fun toString(): String {
        return "Nombres: $firstName,  Apellidos: $lastName, F. Nacimiento: $birthDate, Correo: $email, Contrseña: $password"
    }
}
