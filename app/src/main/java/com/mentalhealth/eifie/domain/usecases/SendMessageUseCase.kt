package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    fun invoke(message: Message) = flow {
        verifyMessageIntegrity()
        messageRepository.saveMessage(message)
        when(val result = messageRepository.sendMessage(message)) {
            is EResult.Success -> result.run {
                emit(messageRepository.saveMessage(data))
            }
            else -> emit(result)
        }
    }


    private fun verifyMessageIntegrity() { }
}