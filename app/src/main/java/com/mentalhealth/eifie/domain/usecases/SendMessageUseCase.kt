package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.OpenAIRepository
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val openAIRepository: OpenAIRepository,
    private val messageRepository: MessageRepository
) {
    fun invoke(message: Message) = flow {
        messageRepository.saveMessage(message)
        emit(
            when(val result = openAIRepository.sendMessage(message)) {
                is DataResult.Success -> result.run {
                    messageRepository.saveMessage(data)
                }
                else -> DataResult.Loading
            }
        )
    }
}