package com.mentalhealth.eifie.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.RecoverPasswordUseCase
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.util.ERR_EMAIL
import com.mentalhealth.eifie.util.ERR_SAVE_USER_SESSION
import com.mentalhealth.eifie.util.TRY_AGAIN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val recoverPassword: RecoverPasswordUseCase
): ViewModel() {

    private val viewState: MutableStateFlow<RecoverPasswordViewState> = MutableStateFlow(RecoverPasswordViewState.Idle)

    val state = viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ViewState.Idle
    )

    fun sendEmailToRecover(email: String) = viewModelScope.launch {
        recoverPassword.invoke(email).onStart {
            viewState.value = RecoverPasswordViewState.Loading
        }.onEach {
            when(it) {
                is EResult.Success -> {
                    viewState.value = RecoverPasswordViewState.Success(it.data)
                }
                is EResult.Error -> {
                    viewState.value = RecoverPasswordViewState.Error(it.error.message ?: ERR_EMAIL)
                }
            }
        }.catch {
            viewState.value = RecoverPasswordViewState.Error("Tiempo de respuesta agotado. Por favor intente otra vez.")
        }.launchIn(viewModelScope)
    }

    fun resolveDialog() {
        viewState.value = RecoverPasswordViewState.Idle
    }
}

sealed class RecoverPasswordViewState {
    object Idle: RecoverPasswordViewState()
    object Loading: RecoverPasswordViewState()
    data class Success(val message: String): RecoverPasswordViewState()
    data class Error(val message: String): RecoverPasswordViewState()
}