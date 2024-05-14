package com.mentalhealth.eifie.ui.profile.detail

import com.mentalhealth.eifie.ui.profile.ProfileItem

data class ProfileDetailData(
    val data: List<ProfileItem> = listOf(),
    val options: List<ProfileItem> = listOf()
)