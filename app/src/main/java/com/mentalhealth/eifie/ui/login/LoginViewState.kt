package com.mentalhealth.eifie.ui.login

import com.mentalhealth.eifie.ui.common.ViewState

sealed class LoginViewState: ViewState() {
    object Idle: LoginViewState()
    object Loading: LoginViewState()
    object Success: LoginViewState()
    data class Error(val message: String): LoginViewState()
}