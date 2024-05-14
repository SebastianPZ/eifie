package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.models.Chat
import com.mentalhealth.eifie.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun invoke() = flow {
        //emit(chatRepository.getChatByUser())
        emit(listOf(
            Chat(
            0,
            "Hola",
            "Gracias",
            "Ayer"),
            Chat(
                0,
                "Hola",
                "Gracias",
                "Ayer"),
            Chat(
                0,
                "Hola",
                "Gracias",
                "Ayer")
        ))
    }
}