package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val psychologistRepository: PsychologistRepository,
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
                is IllegalArgumentException -> sendNotificationToPsychologist()
                else -> Unit
            }
        }
    }

    private suspend fun sendNotificationToPsychologist() {
        when(val user = userRepository.getUser()) {
            is EResult.Error -> Unit
            is EResult.Success -> {
                when(val psychologist = psychologistRepository.getPsychologistById(user.data.psychologistAssigned)) {
                    is EResult.Error -> Unit
                    is EResult.Success -> {
                        notificationRepository.sendFirebaseNotification(
                            psychologist.data.uId,
                            "ALERTA PACIENTE",
                            "El paciente ${user.data.userName} ha cambiado su estado de ánimo y recomendamos comunicarte con él."
                        )
                    }
                }
            }
        }
    }
}