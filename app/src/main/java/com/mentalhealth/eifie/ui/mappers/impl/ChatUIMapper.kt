package com.mentalhealth.eifie.ui.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.ui.models.ChatUI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object ChatUIMapper: Mapper<ChatUI, Chat> {
    override fun mapFromEntity(entity: Chat): ChatUI {
        return ChatUI(
            id = entity.id ?: 0,
            topic = entity.topic,
            photo = entity.photo,
            lastMessage = entity.lastMessage,
            createdDate = entity.createdDate,
            calendarDate = calculateDateToShow(entity.createdDate)
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

    private fun calculateDateToShow(date: LocalDateTime): String {
        val today = LocalDateTime.now()

        val difference: Long = ChronoUnit.DAYS.between(date, today)

        return when(difference) {
            0L -> "Hoy"
            1L -> "Ayer"
            in 2L..5L -> "$difference días"
            else -> {
                date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
            }
        }
    }
}