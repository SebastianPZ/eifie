package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalMessage
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object MessageFirebaseMapper: Mapper<LocalMessage, MutableMap<String, Any?>> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    override fun mapFromEntity(entity: MutableMap<String, Any?>): LocalMessage {
        return LocalMessage(
            id = entity["id"] as Long,
            text = entity["text"] as String,
            role = entity["role"] as String,
            chat = entity["chat"] as Long,
            sendDate = LocalDateTime.parse(entity["send_date"] as String, formatter),
            fromMe = entity["from_me"] as Boolean
        )
    }

    override fun mapToEntity(model: LocalMessage): MutableMap<String, Any?> {
        return hashMapOf(
            "id" to model.id,
            "text" to model.text,
            "role" to model.role,
            "chat" to model.chat,
            "send_date" to model.sendDate.format(formatter),
            "from_me" to model.fromMe
        )
    }

    fun mapToDomain(entity: MutableMap<String, Any?>): Message {
        return Message(
            id = entity["id"] as Long,
            text = entity["text"] as String,
            role = entity["role"] as String,
            chat = entity["chat"] as Long,
            sendDate = LocalDateTime.parse(entity["send_date"] as String, formatter),
            fromMe = entity["from_me"] as Boolean
        )
    }
}