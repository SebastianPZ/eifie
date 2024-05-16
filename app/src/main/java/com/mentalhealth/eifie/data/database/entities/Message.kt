package com.mentalhealth.eifie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "chat") val chat: Long,
    @ColumnInfo(name = "send_date") val sendDate: LocalDateTime,
    @ColumnInfo(name = "from_me") val fromMe: Boolean
) {
    fun toDomain() = com.mentalhealth.eifie.domain.entities.models.Message(
        text = text,
        chat = chat,
        sendDate = sendDate,
        fromMe = fromMe
    )
}