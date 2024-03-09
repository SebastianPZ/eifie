package com.mentalhealth.eifie.data.api.models.request

class RegisterRequest {
    var firstName: String = ""
    var lastName: String = ""
    var birthDate: String = ""
    var email: String = ""
    var password: String = ""
    var checkPassword: String = ""
    var hospital: Int? = null

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
