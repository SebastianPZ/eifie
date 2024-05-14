package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.data.database.entities.Chat
import com.mentalhealth.eifie.data.preferences.EPreferences
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChatRepository {
    private val chatDao = database.chatDao()
    override suspend fun getChatByUser(): DataResult<List<Chat>, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            DataResult.Success(chatDao.findByUser(uId))
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }

    override suspend fun getChatById(chatId: Long): DataResult<Chat, Exception> = withContext(dispatcher) {
        try {
            DataResult.Success(chatDao.findById(chatId))
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }
}