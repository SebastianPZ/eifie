package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot

@Dao
interface SupBotDao {
    @Query("SELECT * FROM localsupbot")
    fun getAll(): List<LocalSupBot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: LocalSupBot)
}