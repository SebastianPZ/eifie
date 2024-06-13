package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mentalhealth.eifie.data.local.database.entities.LocalNotification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM localnotification WHERE user = :user")
    fun getByUser(user: Long): List<LocalNotification>

    @Query("SELECT * FROM localnotification WHERE `key` IN (:keys) AND user = :user")
    fun getByKeyAndUser(keys: List<String>, user: Long): List<LocalNotification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notification: List<LocalNotification>): List<Long>
}