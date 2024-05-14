package com.mentalhealth.eifie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Message(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "chat") val chat: Long,
    @ColumnInfo(name = "send_date") val sendDate: LocalDateTime
)