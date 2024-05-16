package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessageRepository {
    private val messageDao = database.messageDao()
    override suspend fun saveMessage(message: Message): DataResult<Message, Exception> = withContext(dispatcher)  {
        try {
            messageDao.insertAll(
                com.mentalhealth.eifie.data.database.entities.Message(
                    text = message.text,
                    chat = message.chat,
                    sendDate = message.sendDate,
                    fromMe = message.fromMe
                )
            )
            DataResult.Success(message)
        } catch (e: Exception) {
            Log.e("MessageRepository", "Error-SaveMessage", e)
            DataResult.Error(e)
        }
    }

    override suspend fun getMessageByChat(chatId: Long): DataResult<List<Message>, Exception> = withContext(dispatcher) {
        try {
            DataResult.Success(messageDao.findByChat(chatId).map {
                it.toDomain()
            })
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }

    override suspend fun getMessageById(messageId: Long): DataResult<Message, Exception> = withContext(dispatcher) {
        try {
            DataResult.Success(messageDao.findById(messageId).toDomain())
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }
}