package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.mappers.impl.SupporterMapper
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupporterDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val preferences: EPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): SupporterRepository {

    private val supporterDao = database.supporterDao()

    override suspend fun save(supporter: Supporter): EResult<Supporter, Exception> = withContext(dispatcher) {
        try {
            supporterDao.insertAll(SupporterMapper.mapFromEntity(supporter)).run {
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

    override suspend fun update(supporter: Supporter): EResult<Boolean, Exception> = withContext(dispatcher) {
        try {
            supporterDao.update(SupporterMapper.mapFromEntity(supporter))
            EResult.Success(true)
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }
}