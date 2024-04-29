package com.mentalhealth.eifie.domain.entities.states

sealed class RegisterResult {
    object Loading: RegisterResult()
    object Success: RegisterResult()
    data class Error(val message: String): RegisterResult()

}