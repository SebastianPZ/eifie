package com.mentalhealth.eifie.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.usecases.UpdateSupporterUseCase
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.profile.edit.ProfileEditViewState
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

@HiltViewModel(assistedFactory = SupportEditPhotoViewModel.SupportEditPhotoViewModelFactory::class)
class SupportEditPhotoViewModel @AssistedInject constructor(
    @Assisted val supporter: Supporter,
    private val updateSupporterUseCase: UpdateSupporterUseCase
): ViewModel() {

    private val viewState: MutableStateFlow<ProfileEditViewState> = MutableStateFlow(
        ProfileEditViewState.Idle)

    val state = viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ViewState.Idle
    )

    fun updatePhotoResource(uri: Uri) {
        viewState.value = ProfileEditViewState.Editing(uri)
    }

    private fun updateLocalPhoto() = viewModelScope.async {
        updateSupporterUseCase.invoke(supporter, false).singleOrNull()
    }

    fun saveUserPhoto(uri: Uri?, onComplete: () -> Unit = {}) = viewModelScope.launch {
        supporter.photo = uri.toString()

        updateLocalPhoto().await().let {
            when(it) {
                is EResult.Success -> it.run { onComplete() }
                is EResult.Error -> Unit
                else -> Unit
            }
        }
    }


    @AssistedFactory
    fun interface SupportEditPhotoViewModelFactory {
        fun create(supporter: Supporter): SupportEditPhotoViewModel
    }

}