package com.mentalhealth.eifie.data.api.models.request

data class RegisterRequest(
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val email: String = "",
    val password: String = "",
    val hospital: Int? = null
) {

    fun toPsychologistRequest(): PsychologistRequest {
        return PsychologistRequest(
            birthDate = birthDate,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            hospitalId = hospital ?: 0
        )
    }

    fun toPatientRequest(): PatientRequest {
        return PatientRequest(
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
