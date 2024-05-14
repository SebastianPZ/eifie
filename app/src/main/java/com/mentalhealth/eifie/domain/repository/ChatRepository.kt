package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.database.entities.Chat

interface ChatRepository {
    suspend fun getChatByUser(): DataResult<List<Chat>, Exception>
    suspend fun getChatById(chatId: Long): DataResult<Chat, Exception>
}