package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalChat
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Chat

object ChatMapper: Mapper<LocalChat, Chat> {
    override fun mapFromEntity(entity: Chat): LocalChat {
        return LocalChat(
            id = entity.id,
            name = entity.topic,
            date = entity.createdDate,
            lastMessage = entity.lastMessage,
            user = entity.user,
            supBot = entity.supBot,
            photo = entity.photo
        )
    }

    override fun mapToEntity(model: LocalChat): Chat {
        return Chat(
            id = model.id,
            topic = model.name,
            lastMessage = model.lastMessage,
            createdDate = model.date,
            user = model.user,
            supBot = model.supBot,
            photo = model.photo,
        )
    }
}