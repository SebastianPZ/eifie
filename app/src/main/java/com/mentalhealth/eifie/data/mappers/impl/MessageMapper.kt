package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalMessage
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Message

object MessageMapper: Mapper<LocalMessage, Message> {
    override fun mapFromEntity(entity: Message): LocalMessage {
        return LocalMessage(
            text = entity.text,
            chat = entity.chat,
            sendDate = entity.sendDate,
            fromMe = entity.fromMe
        )
    }

    override fun mapToEntity(model: LocalMessage): Message {
        return Message(
            text = model.text,
            chat = model.chat,
            sendDate = model.sendDate,
            fromMe = model.fromMe
        )
    }
}