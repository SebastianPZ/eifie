package com.mentalhealth.eifie.ui.models

import java.time.LocalDateTime

class MessageUI (
    val chat: Long,
    val content: String,
    val isFromMe: Boolean = false,
    val sendDate: LocalDateTime = LocalDateTime.now()
)