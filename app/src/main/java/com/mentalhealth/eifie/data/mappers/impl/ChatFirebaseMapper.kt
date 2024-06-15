package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalChat
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Chat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



object ChatFirebaseMapper: Mapper<LocalChat, MutableMap<String, Any?>> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    override fun mapFromEntity(entity: MutableMap<String, Any?>): LocalChat {
        return LocalChat(
            id = entity["id"] as Long,
            name = entity["name"] as String,
            date = LocalDateTime.parse(entity["date"] as String, formatter),
            lastMessage = entity["last_message"] as String,
            user = entity["user"] as Long,
            supBot = entity["sup_bot"] as Long,
            photo = entity["photo"] as String?
        )
    }

    override fun mapToEntity(model: LocalChat): MutableMap<String, Any?> {
        return hashMapOf(
            "id" to model.id,
            "name" to model.name,
            "date" to model.date.format(formatter),
            "last_message" to model.lastMessage,
            "user" to model.user,
            "sup_bot" to model.supBot,
            "photo" to model.photo,
        )
    }

    fun mapToDomain(entity: MutableMap<String, Any?>): Chat {
        return Chat(
            id = entity["id"] as Long,
            topic = entity["name"] as String,
            createdDate = LocalDateTime.parse(entity["date"] as String, formatter),
            lastMessage = entity["last_message"] as String,
            user = entity["user"] as Long,
            supBot = entity["sup_bot"] as Long,
            photo = entity["photo"] as String?
        )
    }
}