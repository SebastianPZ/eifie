package com.mentalhealth.eifie.ui.common


sealed class ViewState {
    object Idle: ViewState()
    object Loading: ViewState()
    object Success: ViewState()
    data class Error(val message: String): ViewState()
}