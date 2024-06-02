package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.mappers.impl.SupBotMapper
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.SupBot
import com.mentalhealth.eifie.domain.repository.SupBotRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupBotDefaultRepository @Inject constructor(
    private val database: EDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): SupBotRepository {

    private val supBotDao = database.supBotDao()
    override suspend fun saveSupBot(supBot: SupBot): EResult<SupBot, Exception> = withContext(dispatcher) {
        try {
            supBotDao.insertAll(SupBotMapper.mapFromEntity(supBot)).run {
                supBotDao.getAll().last().let { EResult.Success(SupBotMapper.mapToEntity(it)) }
            }
        } catch (e: Exception) {
            EResult.Error(e)
        }
    }

    override suspend fun retrieveSupBot(): EResult<SupBot, Exception> {
        TODO("Not yet implemented")
    }
}