package com.mentalhealth.eifie.ui.profile

import com.mentalhealth.eifie.util.NO_INFO

data class ProfileItem(
    val icon: Int,
    val label: String,
    val value: String = NO_INFO
)