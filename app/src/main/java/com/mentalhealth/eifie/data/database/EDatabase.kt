package com.mentalhealth.eifie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mentalhealth.eifie.data.database.dao.ChatDao
import com.mentalhealth.eifie.data.database.dao.MessageDao
import com.mentalhealth.eifie.data.database.dao.UserDao
import com.mentalhealth.eifie.data.database.entities.Chat
import com.mentalhealth.eifie.data.database.entities.Message
import com.mentalhealth.eifie.data.database.entities.User
import com.mentalhealth.eifie.util.Converter

@Database(
    entities = [User::class, Chat::class, Message::class],
    version = 1)
@TypeConverters(Converter::class)
abstract class EDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
}