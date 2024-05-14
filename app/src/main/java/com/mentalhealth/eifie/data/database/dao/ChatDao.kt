package com.mentalhealth.eifie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mentalhealth.eifie.data.database.entities.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat WHERE id = :id")
    fun findById(id: Long): Chat

    @Query("SELECT * FROM chat WHERE user = :userId")
    fun findByUser(userId: Int): List<Chat>
}