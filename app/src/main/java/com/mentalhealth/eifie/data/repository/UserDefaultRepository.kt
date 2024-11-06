package com.mentalhealth.eifie.data.repository

import android.content.Context
import android.net.Uri
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.network.prepareMultipartRequest
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.LocalUserMapper
import com.mentalhealth.eifie.data.mappers.impl.UserMapper
import com.mentalhealth.eifie.data.models.request.UpdateTokenRequest
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.formatToken
import com.mentalhealth.eifie.util.tokenPreferences
import com.mentalhealth.eifie.util.userMailPreferences
import com.mentalhealth.eifie.util.userPreferences
import com.mentalhealth.eifie.util.userPwdPreferences
import com.mentalhealth.eifie.util.userRolePreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.io.path.outputStream

class UserDefaultRepository @Inject constructor(
    private val context: Context,
    private val api: ApiService,
    private val dataAccess: DataAccess,
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    private val userDao = database.userDao()
    override suspend fun saveUser(user: User, password: String): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            userDao.insertAll(LocalUserMapper.mapFromEntity(user))
            preferences.savePreference(userPreferences, user.profileId)
            preferences.savePreference(userRolePreferences, user.uid)
            preferences.savePreference(userMailPreferences, user.email)
            preferences.savePreference(userPwdPreferences, password)
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
            preferences.savePreference(userPreferences, -1)
            preferences.savePreference(userRolePreferences, -1)
            preferences.savePreference(userMailPreferences, emptyString())
            preferences.savePreference(userPwdPreferences, emptyString())
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun updateUserPicture(userId: Long, photo: Uri): EResult<User, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
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

    override suspend fun updateFirebaseToken(fToken: String): EResult<Boolean, Exception> = withContext(dispatcher) {
        val token = preferences.readPreference(tokenPreferences) ?: emptyString()
        val user = preferences.readPreference(userPreferences) ?: 0
        dataAccess.performApiCall(
            { api.updateFirebaseToken(token.formatToken(), UpdateTokenRequest(fToken.replace('\u00A0',' '), user)) },
            { _ -> true }
        )
    }
}