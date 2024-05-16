package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.Message

fun interface OpenAIRepository {
    suspend fun sendMessage(message: Message): DataResult<Message, Exception>
}