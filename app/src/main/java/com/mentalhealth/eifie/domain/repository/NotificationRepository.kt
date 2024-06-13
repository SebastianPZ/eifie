package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import java.lang.Exception

interface NotificationRepository {
    suspend fun save(notifications: List<Notification>): EResult<Int, Exception>
}