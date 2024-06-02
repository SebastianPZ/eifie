package com.mentalhealth.eifie.data.models.response

data class LoginResponse(
    val patientId: Long? = null,
    val psychologistId: Long? = null,
    val psychologistAssignedId: Long? = null,
    val user: UserResponse? = null,
    val hospital: HospitalResponse? = null
)

fun getLoginErrorMessage(errorCode: Int?): String {
    return when(errorCode) {
        1 -> "El email ingresado no se encuentra registrado."
        2 -> "La contraseña es incorrecta."
        else -> ""
    }
}

fun getAppointmentErrorMessage(errorCode: Int?): String {
    return when(errorCode) {
        1 -> "No cuenta con citas programadas."
        else -> ""
    }
}

