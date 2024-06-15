package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.Chat
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.ChatRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    fun invoke(user: Long) = flow {
        when(val result = chatRepository.getLocalChatsByUser(user)) {
            is EResult.Error -> emit(getBackUp(user))
            is EResult.Success -> result.run {
                if(data.isEmpty()) emit(getBackUp(user))
                else emit(result)
            }
        }
    }

    private suspend fun getBackUp(user: Long): EResult<List<Chat>, Exception> {
        return when(val result = chatRepository.getChatsByUser(user)) {
            is EResult.Error -> result
            is EResult.Success -> chatRepository.saveChats(result.data)
        }
    }
}