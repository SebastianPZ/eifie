package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message

interface MessageRepository {
    suspend fun saveMessage(message: Message): EResult<Message, Exception>
    suspend fun sendMessage(message: Message): EResult<Message, Exception>
    suspend fun getMessageByChat(chatId: Long): EResult<List<Message>, Exception>
    suspend fun getMessageById(messageId: Long): EResult<Message, Exception>
}