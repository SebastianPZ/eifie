package com.mentalhealth.eifie.domain.entities.models

data class Chat(
    val id: Long,
    val name: String,
    val lastMessage: String,
    val createdDate: String,
    val photo: String? = null
)
