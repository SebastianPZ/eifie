package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BackUpChatsUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val supporterRepository: SupporterRepository,
    private val chatRepository: ChatRepository
) {
    fun invoke(supporter: Long) = flow {
        when(val supResult = supporterRepository.backup()) {
            is EResult.Error -> emit(supResult)
            is EResult.Success -> {
                when(val chatResult = chatRepository.backup()) {
                    is EResult.Error -> emit(chatResult)
                    is EResult.Success -> chatResult.run {
                        emit(messageRepository.backup(data, supporter))
                    }
                }
            }
        }
    }
}