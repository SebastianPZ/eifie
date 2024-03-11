package com.mentalhealth.eifie.domain.entities.models

sealed class LoginState {
    object Idle: LoginState()
    object Loading: LoginState()
    data class Error(val message: String): LoginState()
    data class Success(val userSession: UserSession): LoginState()
}