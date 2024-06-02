package com.mentalhealth.eifie.data.models.response

data class PatientResponse(
    val patientId : Long? = null,
    val userId: Long? = null,
    val psychologistAssignedId: Long? = null,
    val birthDate: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val pictureUrl: String? = null
)
