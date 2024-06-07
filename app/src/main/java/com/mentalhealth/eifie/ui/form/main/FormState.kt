package com.mentalhealth.eifie.ui.form.main

sealed class FormState {
    object Idle: FormState()
    object InProgress: FormState()
    object LastQuestion: FormState()
    object Loading: FormState()
    object Error: FormState()

}