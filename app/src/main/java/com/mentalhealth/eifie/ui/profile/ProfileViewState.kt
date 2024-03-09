package com.mentalhealth.eifie.ui.profile

import android.net.Uri
import com.mentalhealth.eifie.ui.common.ViewState

sealed class ProfileViewState: ViewState() {
    object Idle: ProfileViewState()
    object Loading: ProfileViewState()
    data class EditPhoto(val photo: Uri?): ProfileViewState()
    data class Success(val profile: ProfileValues): ProfileViewState()
    object Error: ProfileViewState()
}