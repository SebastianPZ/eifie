package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["key"], unique = true)])
class AppSetting(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val key: String,
    val value: Int,
)