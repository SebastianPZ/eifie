package com.mentalhealth.eifie.ui.profile.detail

import com.mentalhealth.eifie.ui.common.ViewState

sealed class ProfileDetailViewState: ViewState() {
    object Idle: ProfileDetailViewState()
    object Loading: ProfileDetailViewState()
    data class Success(val user: ProfileDetailData): ProfileDetailViewState()
    object Error: ProfileDetailViewState()
}