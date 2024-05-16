package com.mentalhealth.eifie.data.network.models.request

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)