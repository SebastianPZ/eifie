package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.database.entities.Message

interface MessageRepository {
    suspend fun getMessageByChat(chatId: Long): DataResult<List<Message>, Exception>
    suspend fun getMessageById(messageId: Long): DataResult<Message, Exception>
}