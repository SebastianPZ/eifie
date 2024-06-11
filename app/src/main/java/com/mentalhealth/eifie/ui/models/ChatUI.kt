package com.mentalhealth.eifie.ui.models

import java.time.LocalDateTime

class ChatUI(
    val id: Long,
    val topic: String,
    val lastMessage: String,
    val photo: String? = null,
    val createdDate: LocalDateTime,
    val calendarDate: String
)