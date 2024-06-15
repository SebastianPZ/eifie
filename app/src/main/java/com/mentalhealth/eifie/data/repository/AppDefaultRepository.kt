package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): AppRepository {
    private val databaseDao = database.databaseDao()
    override suspend fun clearAllData(): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            database.clearAllTables()
            databaseDao.clearPrimaryKeyIndex()
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }
}