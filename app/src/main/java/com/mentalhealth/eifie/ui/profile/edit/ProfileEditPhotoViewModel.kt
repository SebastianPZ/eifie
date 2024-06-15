package com.mentalhealth.eifie.ui.profile.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.UpdateUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserPhotoUseCase
import com.mentalhealth.eifie.ui.common.ViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProfileEditPhotoViewModel.ProfileEditPhotoViewModelFactory::class)
class ProfileEditPhotoViewModel @AssistedInject constructor(
    @Assisted val user: User,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
    private val updateUserUseCase: UpdateUserInformationUseCase,
) : ViewModel() {

    private val viewState: MutableStateFlow<ProfileEditViewState> = MutableStateFlow(ProfileEditViewState.Idle)

    val state = viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ViewState.Idle
    )

    init {
        viewState.value = ProfileEditViewState.Editing(Uri.parse(user.picture ?: ""))
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
                is EResult.Success -> it.run {
                    handleInformationUpdate(it.data) {
                        onComplete()
                    }.await()
                }
                is EResult.Error -> Unit
                else -> Unit
            }
        }
    }

    private fun handleInformationUpdate(user: User, onComplete: () -> Unit = {}) = viewModelScope.async {
        updateLocalUserData(user).await().let {
            when(it) {
                is EResult.Success -> onComplete()
                else -> Unit
            }
        }
    }

    private fun updateLocalUserData(user: User) = viewModelScope.async {
        updateUserUseCase.invoke(user).singleOrNull()
    }

    @AssistedFactory
    fun interface ProfileEditPhotoViewModelFactory {
        fun create(user: User): ProfileEditPhotoViewModel
    }
}