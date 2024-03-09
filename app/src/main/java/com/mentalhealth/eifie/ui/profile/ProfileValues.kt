package com.mentalhealth.eifie.ui.profile

import com.mentalhealth.eifie.util.NO_INFO
import com.mentalhealth.eifie.util.emptyString

data class ProfileValues(
    val username: String,
    val uri: Any? = null,
    val photo: String = emptyString(),
    val items: List<ProfileItem> = emptyList(),
    val detailItems: List<ProfileItem> = emptyList()
)

data class ProfileItem(
    val icon: Int,
    val label: String,
    val value: String = NO_INFO
)
