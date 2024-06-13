package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["key"], unique = true)])
class LocalNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val key: String,
    val user: Long,
    val date: String
)