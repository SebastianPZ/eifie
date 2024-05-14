package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    fun invoke(chatId: Long) = flow {
        emit(messageRepository.getMessageByChat(chatId = chatId))
    }
}