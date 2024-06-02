package com.mentalhealth.eifie.data.models.response

data class UserResponse(
    val birthDate: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val registerDate: String? = null,
    val role: String? = null,
    val userId: Long? = null,
    val picture: PictureResponse? = null
)