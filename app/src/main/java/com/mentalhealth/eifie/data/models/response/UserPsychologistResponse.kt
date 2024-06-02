package com.mentalhealth.eifie.data.models.response

data class UserPsychologistResponse(
    val psychologistId: Long? = null,
    val accessCode: String? = null,
    val hospital: HospitalResponse? = null,
    val user: UserResponse? = null
)