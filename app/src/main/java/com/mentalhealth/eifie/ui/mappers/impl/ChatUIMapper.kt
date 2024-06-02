package com.mentalhealth.eifie.ui.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.ui.models.ChatUI

object ChatUIMapper: Mapper<ChatUI, Chat> {
    override fun mapFromEntity(entity: Chat): ChatUI {
        return ChatUI(
            id = entity.id ?: 0,
            topic = entity.topic,
            photo = entity.photo,
            lastMessage = entity.lastMessage,
            createdDate = entity.createdDate
        )
    }

    override fun mapToEntity(model: ChatUI): Chat {
        return Chat(
            id = model.id,
            topic = model.topic,
            photo = model.photo,
            lastMessage = model.lastMessage,
            createdDate = model.createdDate
        )
    }
}