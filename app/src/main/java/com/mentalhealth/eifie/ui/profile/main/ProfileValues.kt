package com.mentalhealth.eifie.ui.profile.main

import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.util.emptyString

data class ProfileValues(
    val username: String,
    val photoUri: Any? = null,
    val photo: String = emptyString(),
    val items: List<ProfileItem> = emptyList(),
    val detailItems: List<ProfileItem> = emptyList()
)
