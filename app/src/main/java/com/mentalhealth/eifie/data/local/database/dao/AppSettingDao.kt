package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mentalhealth.eifie.data.local.database.entities.AppSetting

@Dao
interface AppSettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg appSetting: AppSetting)
    @Query("SELECT * FROM appsetting WHERE `key` = :key")
    fun findByKey(key: String): AppSetting
    @Update
    fun update(appSetting: AppSetting)
}