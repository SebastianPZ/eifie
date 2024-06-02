package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.SupBot

interface SupBotRepository {
    suspend fun saveSupBot(supBot: SupBot): EResult<SupBot, Exception>
    suspend fun retrieveSupBot(): EResult<SupBot, Exception>
}