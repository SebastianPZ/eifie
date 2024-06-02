package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetUserChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun invoke() = flow {
        emit(chatRepository.getChatByUser())
    }
}