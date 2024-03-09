package com.mentalhealth.eifie.domain.repository

import android.net.Uri
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.response.UserResponse
import com.mentalhealth.eifie.data.database.entities.User
import java.io.File

interface UserRepository {
    suspend fun saveUser(user: User): DataResult<Boolean, Exception>
    suspend fun updateUser(user: User): DataResult<Boolean, Exception>
    suspend fun getUser(): DataResult<User, Exception>
    suspend fun logoutUser(): DataResult<Boolean, Exception>
    suspend fun updateUserPicture(userId: Int, photo: Uri): DataResult<UserResponse, Exception>
}