package com.mentalhealth.eifie.ui.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.ui.models.MessageUI

object MessageUIMapper: Mapper<MessageUI, Message> {
    override fun mapFromEntity(entity: Message): MessageUI {
        return MessageUI(
            chat = entity.chat,
            content = entity.text,
            isFromMe = entity.fromMe,
            sendDate = entity.sendDate
        )
    }

    override fun mapToEntity(model: MessageUI): Message {
        return Message(
            chat = model.chat,
            text = model.content,
            fromMe = model.isFromMe,
            sendDate = model.sendDate
        )
    }
}