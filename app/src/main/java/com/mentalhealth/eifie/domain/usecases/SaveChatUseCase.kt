package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.models.request.OpenAIRole
import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.util.userPreferences
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class SaveChatUseCase @Inject constructor(
    private val repository: ChatRepository,
    private val messageRepository: MessageRepository
) {

    fun invoke(supporter: Supporter) = flow {

        when(val result = repository.saveChat(Chat(
            topic = "${supporter.name} Chat",
            supBot = supporter.id ?: 0,
            createdDate = LocalDateTime.now()))) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(messageRepository.saveMessage(Message(
                chat = result.data,
                text = supporter.config,
                role = OpenAIRole.SYSTEM.key
            )))
        }
    }

}