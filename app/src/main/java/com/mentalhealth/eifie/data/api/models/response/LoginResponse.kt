package com.mentalhealth.eifie.data.api.models.response

data class LoginResponse(
    val patientId: Int? = null,
    val psychologistId: Int? = null,
    val user: UserResponse? = null,
    val hospital: HospitalResponse? = null
)

fun getErrorMessage(errorCode: Int?): String {
    return when(errorCode) {
        1 -> "El email ingresado no se encuentra registrado."
        2 -> "La contraseña es incorrecta."
        else -> ""
    }
}