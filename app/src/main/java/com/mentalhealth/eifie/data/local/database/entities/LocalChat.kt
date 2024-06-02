package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(foreignKeys = [ForeignKey(
    entity = LocalUser::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("user"),
    onDelete = ForeignKey.CASCADE)])
data class LocalChat(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: LocalDateTime,
    @ColumnInfo(name = "last_message") val lastMessage: String,
    @ColumnInfo(name = "user") var user: Long,
    @ColumnInfo(name = "sup_bot") val supBot: Long,
    @ColumnInfo(name = "photo") val photo: String? = null
)