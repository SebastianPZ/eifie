package com.mentalhealth.eifie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mentalhealth.eifie.data.database.dao.UserDao
import com.mentalhealth.eifie.data.database.entities.User

@Database(
    entities = [User::class],
    version = 1)
abstract class EDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}