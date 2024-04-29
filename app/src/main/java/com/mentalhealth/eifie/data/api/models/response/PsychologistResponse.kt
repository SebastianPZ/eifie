package com.mentalhealth.eifie.data.api.models.response

data class PsychologistResponse(
    val psychologistId: Int? = null,
    val accessCode: String? = null,
    val hospital: HospitalResponse? = null,
    val user: UserResponse? = null
)