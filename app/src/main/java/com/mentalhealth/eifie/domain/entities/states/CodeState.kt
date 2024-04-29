package com.mentalhealth.eifie.domain.entities.states

import com.mentalhealth.eifie.domain.entities.models.Psychologist

sealed class CodeState {
    object Idle : CodeState()
    object Loading : CodeState()
    data class Success(val psychologist: Psychologist) : CodeState()
    data class Error(val error: String) : CodeState()
}