package com.mentalhealth.eifie.data.models.response

data class PatientResponse(
    val birthDate: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val patientId : Long? = null,
    val pictureUrl: String? = null,
    val psychologistAssignedId: Long? = null,
    val userId: Long? = null
)
