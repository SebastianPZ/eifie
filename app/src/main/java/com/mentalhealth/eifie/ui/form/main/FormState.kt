package com.mentalhealth.eifie.ui.form.main

sealed class FormState {
    object Idle: FormState()
    object InProgress: FormState()
    object LastQuestion: FormState()
    object Loading: FormState()
    data class Success(val message: String): FormState()
    data class Error(val message: String): FormState()
    data class Done(val message: String): FormState()

}