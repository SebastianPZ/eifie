package com.mentalhealth.eifie.domain.entities

data class Notification(
    val title: String,
    val content: String,
    val action: String? = null
)