package com.mentalhealth.eifie.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.SupporterFirebaseMapper
import com.mentalhealth.eifie.data.mappers.impl.SupporterMapper
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupporterDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): SupporterRepository {


    private val TAG = SupporterDefaultRepository::class.java.simpleName
    private val supporterDao = database.supporterDao()

    override suspend fun save(supporter: Supporter): EResult<Supporter, Exception> = withContext(dispatcher) {
        try {
            val user = preferences.readPreference(userPreferences) ?: 0
            supporterDao.insertAll(SupporterMapper.mapFromEntity(supporter.apply { id = user })).run {
                supporterDao.getByUser(supporter.user).last().let { EResult.Success(SupporterMapper.mapToEntity(it)) }
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getByUser(): EResult<Supporter, Exception> = withContext(dispatcher) {
        try {
            val user = preferences.readPreference(userPreferences) ?: 0
            supporterDao.getByUser(user).run {
                EResult.Success(SupporterMapper.mapToEntity(first()))
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getSupporterBackUp(): EResult<Supporter, Exception> = withContext(dispatcher) {
        try {
            val user = preferences.readPreference(userPreferences) ?: 0
            val db = Firebase.firestore

            val result = db.collection("supporters")
                .document(user.toString())
                .get()
                .await()

            if (result.exists()) {
                supporterDao.insertAll(SupporterFirebaseMapper.mapFromEntity(result.data!!)).run {
                    supporterDao.getByUser(user).last().let { EResult.Success(SupporterMapper.mapToEntity(it)) }
                }
            } else {
                EResult.Error(Exception("Document not found"))
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun update(supporter: Supporter): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            supporterDao.update(SupporterMapper.mapFromEntity(supporter))
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun backup(): EResult<Boolean, Exception> = withContext(dispatcher) {
        val user = preferences.readPreference(userPreferences) ?: 0
        val supporter = supporterDao.getByUser(user)

        val db = Firebase.firestore

        supporter.first().run {
            db.collection("supporters")
                .document(this.userId.toString())
                .set(SupporterFirebaseMapper.mapToEntity(this))
                .addOnSuccessListener { _ ->
                    Log.d(TAG, "Success firebase")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error firebase")
                }
        }

        EResult.Success(true)
    }
}