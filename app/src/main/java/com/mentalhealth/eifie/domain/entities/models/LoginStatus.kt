package com.mentalhealth.eifie.domain.entities.models

sealed class LoginStatus {
    object Idle: LoginStatus()
    object Loading: LoginStatus()
    data class Error(val message: String): LoginStatus()
    data class Success(val userSession: UserSession): LoginStatus()
}