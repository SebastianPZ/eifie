package com.mentalhealth.eifie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalhealth.eifie.data.database.entities.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg message: Message)

    @Query("SELECT * FROM message WHERE id = :id")
    fun findById(id: Long): Message

    @Query("SELECT * FROM message WHERE chat = :chatId")
    fun findByChat(chatId: Long): List<Message>
}