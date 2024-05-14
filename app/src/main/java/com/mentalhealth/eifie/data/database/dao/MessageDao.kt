package com.mentalhealth.eifie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mentalhealth.eifie.data.database.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE id = :id")
    fun findById(id: Long): Message

    @Query("SELECT * FROM message WHERE chat = :chatId")
    fun findByChat(chatId: Long): List<Message>
}