package com.mentalhealth.eifie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mentalhealth.eifie.data.local.database.dao.ChatDao
import com.mentalhealth.eifie.data.local.database.dao.MessageDao
import com.mentalhealth.eifie.data.local.database.dao.SupBotDao
import com.mentalhealth.eifie.data.local.database.dao.UserDao
import com.mentalhealth.eifie.data.local.database.entities.LocalChat
import com.mentalhealth.eifie.data.local.database.entities.LocalMessage
import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot
import com.mentalhealth.eifie.data.local.database.entities.LocalUser
import com.mentalhealth.eifie.util.Converter

@Database(
    entities = [LocalUser::class, LocalChat::class, LocalMessage::class, LocalSupBot::class],
    version = 1)
@TypeConverters(Converter::class)
abstract class EDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun supBotDao(): SupBotDao
}