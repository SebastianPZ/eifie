package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.database.entities.LocalChat
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.ChatMapper
import com.mentalhealth.eifie.domain.entities.Chat
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
    override suspend fun saveChat(chat: Chat): EResult<Long, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            EResult.Success(chatDao.insert(ChatMapper.mapFromEntity(chat).apply {
                user = uId
            }))
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getChatByUser(): EResult<List<Chat>, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            EResult.Success(chatDao.findByUser(uId).map { ChatMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getChatById(chatId: Long): EResult<Chat, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(chatDao.findById(chatId).let { ChatMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }
}