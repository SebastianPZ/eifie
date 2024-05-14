package com.mentalhealth.eifie.ui.register

import com.mentalhealth.eifie.ui.common.ViewState

sealed class RegisterViewState: ViewState() {
    object Idle: RegisterViewState()
    object Loading: RegisterViewState()
    data class Success(val user: Long): RegisterViewState()
    data class Error(val message: String): RegisterViewState()
}