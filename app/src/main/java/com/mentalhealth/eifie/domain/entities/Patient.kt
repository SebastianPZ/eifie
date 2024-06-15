package com.mentalhealth.eifie.domain.entities

data class Patient(
    val id: Long = 0,
    val psychologistAssigned: Long = 0,
    val firstname: String = "",
    val lastname: String = "",
    val username: String = "",
    val birthDate: String = "",
    val email: String = "",
    val status: Int = 0,
    val lastStatusUpdateDate: String = "",
    val picture: String? = null
) {
    fun status(status: Int = this.status): String {
        return when(status) {
            1 -> "Probibilidad de depresión inexistente"
            2 -> "Probabilidad de depresión existente"
            else -> "Pendiente de evaluación"
        }
    }
}