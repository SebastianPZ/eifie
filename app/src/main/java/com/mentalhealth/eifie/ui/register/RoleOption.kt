package com.mentalhealth.eifie.ui.register

data class RoleOption(
    val id: Int,
    val text: String,
    val abb: String,
    val icon: Int,
    var selected: Boolean
)