package com.mentalhealth.eifie.ui.profile.main

import android.net.Uri
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.profile.ProfileItem

sealed class ProfileViewState: ViewState() {
    object Idle: ProfileViewState()
    object Loading: ProfileViewState()
    data class Success(val items: List<ProfileItem>): ProfileViewState()
    object Error: ProfileViewState()
}