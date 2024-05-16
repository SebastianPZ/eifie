package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.Message

interface MessageRepository {
    suspend fun saveMessage(message: Message): DataResult<Message, Exception>
    suspend fun getMessageByChat(chatId: Long): DataResult<List<Message>, Exception>
    suspend fun getMessageById(messageId: Long): DataResult<Message, Exception>
}