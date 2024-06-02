package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.mappers.impl.MessageMapper
import com.mentalhealth.eifie.data.models.request.Question
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val api: OpenAIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessageRepository {
    private val messageDao = database.messageDao()
    override suspend fun saveMessage(message: Message): EResult<Message, Exception> = withContext(dispatcher)  {
        try {
            messageDao.insertAll(MessageMapper.mapFromEntity(message))
            EResult.Success(message)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun sendMessage(message: Message): EResult<Message, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                api.sendMessage(
                    Question(messages = listOf(
                        com.mentalhealth.eifie.data.models.request.Message(
                            role = "user",
                            content = message.text
                        )
                    ))
                )
            },
            { answer -> Message(
                text = answer?.choices?.first()?.message?.content ?: "",
                chat = message.chat
            ) }
        )
    }

    override suspend fun getMessageByChat(chatId: Long): EResult<List<Message>, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(messageDao.findByChat(chatId).map { MessageMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            EResult.Error(e)
        }
    }

    override suspend fun getMessageById(messageId: Long): EResult<Message, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(messageDao.findById(messageId).let { MessageMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            EResult.Error(e)
        }
    }
}