package com.mentalhealth.eifie.data.repository

import android.content.Context
import android.net.Uri
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.data.network.prepareMultipartRequest
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.LocalUserMapper
import com.mentalhealth.eifie.data.mappers.impl.UserMapper
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.tokenPreferences
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.io.path.outputStream

class UserDefaultRepository @Inject constructor(
    private val context: Context,
    private val api: ApiService,
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    private val userDao = database.userDao()
    override suspend fun saveUser(user: User): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            userDao.insertAll(LocalUserMapper.mapFromEntity(user))
            preferences.savePreference(userPreferences, user.profileId)
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun updateUser(user: User): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            userDao.updateUser(LocalUserMapper.mapFromEntity(user))
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun getUser(): EResult<User, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            userDao.findById(uId).let {
                EResult.Success(LocalUserMapper.mapToEntity(it))
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun logoutUser(): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            preferences.savePreference(tokenPreferences, emptyString())
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun updateUserPicture(userId: Long, photo: Uri): EResult<User, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                val file = kotlin.io.path.createTempFile()
                val inputStream = context.contentResolver.openInputStream(photo)

                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }

                val partRequest = prepareMultipartRequest(file = file.toFile())
                api.registerProfilePicture(userId, profilePic = partRequest.second)
            },
            { response -> response?.data?.let { UserMapper.mapToEntity(it) } }
        )
    }
}