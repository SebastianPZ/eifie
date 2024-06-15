package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val notificationRepository: NotificationRepository
) {
    fun invoke(message: Message) = flow {
        verifyMessageIntegrity(message.text)
        messageRepository.saveMessage(message)
        when(val result = messageRepository.sendMessage(message)) {
            is EResult.Success -> result.run {
                emit(messageRepository.saveMessage(data))
            }
            else -> emit(result)
        }
    }


    private suspend fun verifyMessageIntegrity(text: String) {
        try {
            val dictionary = setOf("triste", "mal", "muerte")

            val words = text.split("\\s+".toRegex())

            for (word in words) {
                require(word.lowercase() !in dictionary) { "Paciente triste" }
            }
        } catch (e: Exception) {
            when(e) {
                is IllegalArgumentException -> notificationRepository.sendFirebaseNotification()
                else -> Unit
            }
        }
    }
}