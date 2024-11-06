package com.mentalhealth.eifie.ui.profile.edit

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.UpdatePasswordUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updatePassword: UpdatePasswordUseCase
): LazyViewModel() {

    fun updatePassword(actualPassword: String, newPassword: String) = viewModelScope.launch {
        updatePassword.invoke(actualPassword, newPassword).onStart {
            viewState.value = ViewState.Loading
        }.onEach {
            when(it) {
                is EResult.Error -> viewState.value = ViewState.Error(it.error.message ?: "Error al actualizar contraseña")
                is EResult.Success -> viewState.value = ViewState.Success
            }
        }.catch {
            viewState.value = ViewState.Error(it.message ?: "Error al actualizar contraseña")
        }.launchIn(viewModelScope)
    }

    fun resolveDialog() {
        viewState.value = ViewState.Idle
    }
}