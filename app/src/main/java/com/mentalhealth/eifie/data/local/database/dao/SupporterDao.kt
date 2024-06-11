package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot

@Dao
interface SupporterDao {
    @Query("SELECT * FROM localsupbot WHERE userId = :user")
    fun getByUser(user: Long): List<LocalSupBot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg supporter: LocalSupBot)

    @Update
    fun update(supporter: LocalSupBot)
}