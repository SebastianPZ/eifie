package com.mentalhealth.eifie.ui.profile.edit

import android.net.Uri
import com.mentalhealth.eifie.ui.common.ViewState

sealed class ProfileEditViewState: ViewState() {
    object Idle: ProfileEditViewState()
    object Loading: ProfileEditViewState()
    data class Editing(val photo: Uri?): ProfileEditViewState()
    object Success: ProfileEditViewState()
    object Error: ProfileEditViewState()
}