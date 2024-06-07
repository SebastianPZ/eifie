package com.mentalhealth.eifie.data.models.response

data class UserPatientResponse(
    val patientId: Long? = null,
    val psychologistAssignedId: Long? = null,
    val user: UserResponse? = null
)