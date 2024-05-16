package com.mentalhealth.eifie.data.network.models.response

data class PatientResponse(
    val patientId: Long? = null,
    val psychologistAssignedId: Int? = null,
    val user: UserResponse? = null
)