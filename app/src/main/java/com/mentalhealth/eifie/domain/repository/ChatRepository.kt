package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.entities.EResult

interface ChatRepository {
    suspend fun saveChat(chat: Chat): EResult<Long, Exception>
    suspend fun saveChats(chats: List<Chat>): EResult<List<Chat>, Exception>
    suspend fun getLocalChatsByUser(user: Long): EResult<List<Chat>, Exception>
    suspend fun getChatsByUser(user: Long): EResult<List<Chat>, Exception>
    suspend fun getChatById(chatId: Long): EResult<Chat, Exception>
    suspend fun backup(): EResult<List<Long>, Exception>
}