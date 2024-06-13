package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.entities.EResult

interface ChatRepository {
    suspend fun saveChat(chat: Chat): EResult<Long, Exception>
    suspend fun getChatByUser(): EResult<List<Chat>, Exception>
    suspend fun getChatById(chatId: Long): EResult<Chat, Exception>
}