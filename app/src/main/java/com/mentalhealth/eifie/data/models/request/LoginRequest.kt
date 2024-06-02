package com.mentalhealth.eifie.data.models.request

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)