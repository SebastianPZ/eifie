package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult

fun interface AppRepository {
    suspend fun clearAllData(): EResult<Boolean, Exception>
}