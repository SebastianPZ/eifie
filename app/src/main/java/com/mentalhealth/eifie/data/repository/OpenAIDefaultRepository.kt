package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.network.models.request.Question
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.domain.repository.OpenAIRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class OpenAIDefaultRepository @Inject constructor(
    private val api: OpenAIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : OpenAIRepository {
    override suspend fun sendMessage(message: Message): DataResult<Message, Exception> = withContext(dispatcher) {
        performApiCall(
            {
                api.sendMessage(Question(messages = listOf(
                    com.mentalhealth.eifie.data.network.models.request.Message(
                        role = "user",
                        content = message.text
                    )
                )))
            },
            { answer -> Message(
                text = answer?.choices?.first()?.message?.content ?: "",
                chat = message.chat
            ) }
        )
    }
}