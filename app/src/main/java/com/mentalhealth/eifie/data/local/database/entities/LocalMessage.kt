package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mentalhealth.eifie.domain.entities.Message
import java.time.LocalDateTime

@Entity(foreignKeys = [ForeignKey(
    entity = LocalChat::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("chat"),
    onDelete = ForeignKey.CASCADE)])
data class LocalMessage(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "chat") val chat: Long,
    @ColumnInfo(name = "send_date") val sendDate: LocalDateTime,
    @ColumnInfo(name = "from_me") val fromMe: Boolean
)