package com.mentalhealth.eifie.domain.entities

import java.time.LocalDateTime

data class Message(
    val text: String,
    val chat: Long,
    val sendDate: LocalDateTime = LocalDateTime.now(),
    val fromMe: Boolean = false
)
