package com.mentalhealth.eifie.domain.repository

import android.net.Uri
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.User

interface UserRepository {
    suspend fun saveUser(user: User, password: String): EResult<Boolean, Exception>
    suspend fun updateUser(user: User): EResult<Boolean, Exception>
    suspend fun getUser(): EResult<User, Exception>
    suspend fun logoutUser(): EResult<Boolean, Exception>
    suspend fun updateUserPicture(userId: Long, photo: Uri): EResult<User, Exception>
    suspend fun updateFirebaseToken(fToken: String): EResult<Boolean, Exception>
}