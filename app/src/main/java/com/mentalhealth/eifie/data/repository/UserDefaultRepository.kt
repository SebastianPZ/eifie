package com.mentalhealth.eifie.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.response.UserResponse
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.data.network.prepareMultipartRequest
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.data.database.entities.User
import com.mentalhealth.eifie.data.preferences.EPreferences
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
    override suspend fun saveUser(user: User): DataResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            userDao.insertAll(user)
            preferences.savePreference(userPreferences, user.profileId)
            DataResult.Success(true)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }

    override suspend fun updateUser(user: User): DataResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            userDao.updateUser(user)
            DataResult.Success(true)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error-SaveUser", e)
            DataResult.Error(e)
        }
    }

    override suspend fun getUser(): DataResult<User, Exception> = withContext(dispatcher) {
        try {
            val uId = preferences.readPreference(userPreferences) ?: 0
            DataResult.Success(userDao.findById(uId))
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun logoutUser(): DataResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            preferences.savePreference(tokenPreferences, emptyString())
            DataResult.Success(true)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun updateUserPicture(userId: Int, photo: Uri): DataResult<UserResponse, Exception> = withContext(dispatcher) {
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
            { response -> response?.data }
        )
    }
}