package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPatientChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun invoke(patient: Long) = flow {
        emit(chatRepository.getChatsByUser(patient))
    }
}