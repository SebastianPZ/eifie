package com.mentalhealth.eifie.ui.register.psychologist

import com.mentalhealth.eifie.ui.common.ViewState

sealed class RegisterPsychologistViewState: ViewState() {
    object Idle: RegisterPsychologistViewState()
    object Loading: RegisterPsychologistViewState()
    object Success: RegisterPsychologistViewState()
    object Error: RegisterPsychologistViewState()
}