package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message

interface MessageRepository {
    suspend fun saveMessage(message: Message): EResult<Message, Exception>
    suspend fun saveMessages(messages: List<Message>): EResult<List<Message>, Exception>
    suspend fun sendMessage(message: Message): EResult<Message, Exception>
    suspend fun getLocalMessageByChat(chatId: Long): EResult<List<Message>, Exception>
    suspend fun getMessageByChat(chatId: Long, user: Long? = null): EResult<List<Message>, Exception>
    suspend fun getMessageById(messageId: Long): EResult<Message, Exception>
    suspend fun backup(chatIds: List<Long>, supporter: Long): EResult<Boolean, Exception>
}