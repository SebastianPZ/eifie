package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalSupBot(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val userId: Long? = null,
    val name: String,
    val config: String,
    val photo: String? = null
)