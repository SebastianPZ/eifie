package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.ChatFirebaseMapper
import com.mentalhealth.eifie.data.mappers.impl.ChatMapper
import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChatRepository {
    private val TAG = ChatDefaultRepository::class.java.simpleName
    private val chatDao = database.chatDao()
    override suspend fun saveChat(chat: Chat): EResult<Long, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            EResult.Success(chatDao.insert(ChatMapper.mapFromEntity(chat).apply {
                user = uId
            }))
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun saveChats(chats: List<Chat>): EResult<List<Chat>, Exception> = withContext(dispatcher) {
        try {
            for(chat in chats) {
                chatDao.insert(ChatMapper.mapFromEntity(chat))
            }
            val user = preferences.readPreference(userPreferences) ?: 0
            EResult.Success(chatDao.findByUser(user).map { ChatMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getLocalChatsByUser(user: Long): EResult<List<Chat>, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(chatDao.findByUser(user).map { ChatMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getChatsByUser(user: Long): EResult<List<Chat>, Exception> = withContext(dispatcher) {
        try {
            val db = Firebase.firestore
            val result = db.collection("supporters")
                .document(user.toString())
                .collection("chats")
                .get()
                .await()

            if (!result.isEmpty) {
                EResult.Success(result.map { ChatFirebaseMapper.mapToDomain(it.data) })
            } else {
                EResult.Error(Exception("Document not found"))
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getChatById(chatId: Long): EResult<Chat, Exception> = withContext(dispatcher) {
        try {
            EResult.Success(chatDao.findById(chatId).let { ChatMapper.mapToEntity(it) })
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun backup(): EResult<List<Long>, Exception> = withContext(dispatcher) {
        val uId = preferences.readPreference(userPreferences) ?: 0
        val chats = chatDao.findByUser(uId)

        val ids = mutableListOf<Long>()
        val db = Firebase.firestore

        for(chat in chats) {
            db.collection("supporters")
                .document(chat.supBot.toString())
                .collection("chats")
                .document(chat.id.toString())
                .set(ChatFirebaseMapper.mapToEntity(chat))
                .addOnSuccessListener { _ ->
                    ids.add(chat.id ?: 0)
                    Log.d(TAG, "Success firebase")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error firebase")
                }.await()
        }

        EResult.Success(ids)
    }
}