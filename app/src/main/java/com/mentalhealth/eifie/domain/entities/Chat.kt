package com.mentalhealth.eifie.domain.entities

import java.time.LocalDateTime

data class Chat(
    val id: Long? = null,
    val user: Long = 0,
    val supBot: Long = 0,
    val topic: String = "",
    val photo: String? = null,
    val lastMessage: String = "",
    val createdDate: LocalDateTime
)
