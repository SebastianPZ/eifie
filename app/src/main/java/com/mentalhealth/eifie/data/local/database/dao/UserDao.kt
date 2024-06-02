package com.mentalhealth.eifie.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mentalhealth.eifie.data.local.database.entities.LocalUser

@Dao
interface UserDao {
    @Query("SELECT * FROM localuser")
    fun getAll(): List<LocalUser>

    @Query("SELECT * FROM localuser WHERE profileId = :uid")
    fun findById(uid: Long): LocalUser

    @Query("SELECT * FROM localuser WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): LocalUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: LocalUser)

    @Update
    fun updateUser(user: LocalUser)

    @Delete
    fun delete(user: LocalUser)
}