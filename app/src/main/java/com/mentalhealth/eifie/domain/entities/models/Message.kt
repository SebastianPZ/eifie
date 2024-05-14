package com.mentalhealth.eifie.domain.entities.models

import java.util.Date

data class Message(
    val id: Long,
    val text: String,
    val sendDate: Date,
    val sender: Boolean = false
)
