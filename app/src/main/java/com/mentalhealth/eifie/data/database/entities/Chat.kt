package com.mentalhealth.eifie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Chat(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "user") val user: Int,
    @ColumnInfo(name = "chatBot") val chatBot: Int,
    @ColumnInfo(name = "date") val date: LocalDateTime,
    @ColumnInfo(name = "last_message") val lastMessage: String
)