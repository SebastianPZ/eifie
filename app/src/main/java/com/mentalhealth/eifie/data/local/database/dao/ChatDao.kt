package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalhealth.eifie.data.local.database.entities.LocalChat

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: LocalChat): Long
    @Query("SELECT * FROM localchat WHERE id = :id")
    fun findById(id: Long): LocalChat

    @Query("SELECT * FROM localchat WHERE user = :userId")
    fun findByUser(userId: Long): List<LocalChat>
}