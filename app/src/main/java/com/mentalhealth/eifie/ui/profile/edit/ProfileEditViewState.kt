package com.mentalhealth.eifie.ui.profile.edit

import android.net.Uri

sealed class ProfileEditViewState {
    object Idle: ProfileEditViewState()
    object Loading: ProfileEditViewState()
    data class Editing(val photo: Uri?): ProfileEditViewState()
    object Success: ProfileEditViewState()
    object Error: ProfileEditViewState()
}