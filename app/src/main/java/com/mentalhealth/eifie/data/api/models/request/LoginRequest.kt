package com.mentalhealth.eifie.data.api.models.request

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)