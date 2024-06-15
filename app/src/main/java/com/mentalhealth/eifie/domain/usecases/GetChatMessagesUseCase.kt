package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.repository.MessageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    fun invoke(chatId: Long) = flow {
        when(val result = messageRepository.getLocalMessageByChat(chatId = chatId)) {
            is EResult.Error -> emit(getBackUp(chatId))
            is EResult.Success -> result.run {
                if(data.isEmpty()) emit(getBackUp(chatId))
                else emit(result)
            }
        }
    }

    private suspend fun getBackUp(chatId: Long): EResult<List<Message>, Exception> {
        return when(val result = messageRepository.getMessageByChat(chatId)) {
            is EResult.Error -> result
            is EResult.Success -> messageRepository.saveMessages(result.data)
        }
    }
}