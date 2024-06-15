package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.ChatFirebaseMapper
import com.mentalhealth.eifie.data.mappers.impl.ChatMapper
import com.mentalhealth.eifie.data.mappers.impl.MessageFirebaseMapper
import com.mentalhealth.eifie.data.mappers.impl.MessageMapper
import com.mentalhealth.eifie.data.models.request.Question
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.data.network.performOpenAICall
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val api: OpenAIService,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessageRepository {
    private val TAG = MessageDefaultRepository::class.java.simpleName
    private val messageDao = database.messageDao()
    private val supportDao = database.supporterDao()
    override suspend fun saveMessage(message: Message): EResult<Message, Exception> = withContext(dispatcher)  {
        try {
            messageDao.insertAll(MessageMapper.mapFromEntity(message))
            EResult.Success(message)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun saveMessages(messages: List<Message>): EResult<List<Message>, Exception> = withContext(dispatcher) {
        try {
            for(message in messages) {
                messageDao.insertAll(MessageMapper.mapFromEntity(message))
            }
            EResult.Success(messageDao.findByChat(messages.first().chat).map { MessageMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun sendMessage(message: Message): EResult<Message, Exception> = withContext(dispatcher) {
        performOpenAICall(
            {
                api.sendMessage(
                    Question(messages = messageDao.findByChat(message.chat).map {
                        com.mentalhealth.eifie.data.models.request.Message(
                            role = it.role,
                            content = it.text
                        )
                    })
                )
            },
            { answer -> Message(
                text = answer?.choices?.first()?.message?.content ?: "",
                role = answer?.choices?.first()?.message?.role ?: "",
                chat = message.chat
            ) }
        )
    }

    override suspend fun getLocalMessageByChat(chatId: Long): EResult<List<Message>, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(messageDao.findByChat(chatId).map { MessageMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            EResult.Error(e)
        }
    }

    override suspend fun getMessageByChat(chatId: Long, user: Long?): EResult<List<Message>, Exception> = withContext(dispatcher) {
        try {
            val db = Firebase.firestore
            val userId = user ?: preferences.readPreference(userPreferences) ?: 0

            val result = db.collection("supporters")
                .document(userId.toString())
                .collection("chats")
                .document(chatId.toString())
                .collection("messages")
                .get().await()

            if (!result.isEmpty) {
                EResult.Success(result.map { MessageFirebaseMapper.mapToDomain(it.data) })
            } else {
                EResult.Error(Exception("Document not found"))
            }

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

    override suspend fun backup(chatIds: List<Long>, supporter: Long): EResult<Boolean, Exception> = withContext(dispatcher) {
        for(chatId in chatIds) {
            val messages = messageDao.findByChat(chatId)

            val db = Firebase.firestore

            for(message in messages) {
                db.collection("supporters")
                    .document(supporter.toString())
                    .collection("chats")
                    .document(message.chat.toString())
                    .collection("messages")
                    .document(message.id.toString())
                    .set(MessageFirebaseMapper.mapToEntity(message))
                    .addOnSuccessListener { _ ->
                        Log.d(TAG, "Success firebase")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Error firebase")
                    }
            }
        }

        EResult.Success(true)
    }
}