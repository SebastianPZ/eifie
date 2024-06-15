package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import kotlinx.coroutines.CoroutineScope

interface SupporterRepository {
    suspend fun save(supporter: Supporter): EResult<Supporter, Exception>
    suspend fun getByUser(): EResult<Supporter, Exception>
    suspend fun getSupporterBackUp(): EResult<Supporter, Exception>
    suspend fun update(supporter: Supporter): EResult<Boolean, Exception>
    suspend fun backup(): EResult<Boolean, Exception>
}