package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPatientMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    fun invoke(chat: Long, patient: Long) = flow {
        emit(messageRepository.getMessageByChat(chat, patient))
    }
}