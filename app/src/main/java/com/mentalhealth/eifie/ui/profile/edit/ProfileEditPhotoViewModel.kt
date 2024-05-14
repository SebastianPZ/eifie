package com.mentalhealth.eifie.ui.profile.edit

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.usecases.UpdateUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserPhotoUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProfileEditPhotoViewModel.ProfileEditPhotoViewModelFactory::class)
class ProfileEditPhotoViewModel @AssistedInject constructor(
    @Assisted val user: UserSession,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
    private val updateUserUseCase: UpdateUserInformationUseCase,
) : LazyViewModel() {

    init {
        viewState.value = ProfileEditViewState.Editing(Uri.parse(user.picture))
    }

    fun updatePhotoResource(uri: Uri) {
        viewState.value = ProfileEditViewState.Editing(uri)
    }

    private fun updateRemoteUserPhoto() = viewModelScope.async {
        updateUserPhotoUseCase.invoke(user).singleOrNull()
    }

    fun saveUserPhoto(uri: Uri?, onComplete: () -> Unit = {}) = viewModelScope.launch {
        user.picture = uri.toString()

        updateRemoteUserPhoto().await().let {
            when(it) {
                is DataResult.Success -> it.run {
                    handleInformationUpdate(it.data) {
                        onComplete()
                    }.await()
                }
                is DataResult.Error -> Unit
                else -> Unit
            }
        }
    }

    private fun handleInformationUpdate(user: UserSession, onComplete: () -> Unit = {}) = viewModelScope.async {
        updateLocalUserData(user).await().let {
            when(it) {
                is DataResult.Success -> onComplete()
                else -> Unit
            }
        }
    }

    private fun updateLocalUserData(user: UserSession) = viewModelScope.async {
        updateUserUseCase.invoke(user).singleOrNull()
    }

    @AssistedFactory
    fun interface ProfileEditPhotoViewModelFactory {
        fun create(user: UserSession): ProfileEditPhotoViewModel
    }
}