package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import kotlin.Exception

interface NotificationRepository {
    suspend fun save(notifications: List<Notification>): EResult<Int, Exception>
    suspend fun sendFirebaseNotification(): EResult<Boolean, Exception>
}