package com.mentalhealth.eifie.ui.form.main

sealed class FormState {
    object Idle: FormState()
    object InProgress: FormState()
    object LastQuestion: FormState()
    object Complete: FormState()
    object Canceled: FormState()
    object Error: FormState()

}