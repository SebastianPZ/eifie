package com.mentalhealth.eifie.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mentalhealth.eifie.data.database.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findById(uid: Int): User

    @Query("SELECT * FROM user WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun delete(user: User)
}