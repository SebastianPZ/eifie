package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalhealth.eifie.data.local.database.entities.LocalMessage

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg message: LocalMessage)

    @Query("SELECT * FROM localmessage WHERE id = :id")
    fun findById(id: Long): LocalMessage

    @Query("SELECT * FROM localmessage WHERE chat = :chatId")
    fun findByChat(chatId: Long): List<LocalMessage>
}