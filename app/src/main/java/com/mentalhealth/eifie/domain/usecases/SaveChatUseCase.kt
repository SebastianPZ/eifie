package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class SaveChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {

    fun invoke(supBot: Long) = flow {
        emit(repository.saveChat(
            Chat(
                topic = "Prueba",
                supBot = supBot,
                createdDate = LocalDateTime.now()))
        )
    }

}