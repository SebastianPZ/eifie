package com.mentalhealth.eifie.ui.chat.chatbox

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.domain.usecases.GetChatMessagesUseCase
import com.mentalhealth.eifie.domain.usecases.SendMessageUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatBoxViewModel.ChatBoxViewModelFactory::class)
class ChatBoxViewModel @AssistedInject constructor(
    @Assisted private val chatId: Long,
    private val getChatMessages: GetChatMessagesUseCase,
    private val sendMessage: SendMessageUseCase
): LazyViewModel() {

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())

    val messages = _messages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = mutableListOf()
    )

    init {
        initChatMessages()
    }

    private fun initChatMessages() = viewModelScope.launch {
        getChatMessages.invoke(chatId = chatId)
            .onStart { viewState.value = ChatBoxViewState.Loading }
            .onEach {
                when(it) {
                    is DataResult.Success -> it.run {
                        _messages.value = this.data.toMutableList()
                    }
                    else -> viewState.value = ChatBoxViewState.Error
                }
            }
            .catch { viewState.value = ChatBoxViewState.Error }
            .launchIn(viewModelScope)
    }

    fun sendMessage(message: String) {
        operateMessage(text = message)
    }

    private fun operateMessage(text: String) = viewModelScope.launch {
        Message(text = text, chat = chatId, fromMe = true).run {
            _messages.value = _messages.value.plus(this)

            sendMessage.invoke(this)
                .onStart {  }
                .onEach {
                    when(it) {
                        is DataResult.Success -> it.run {
                            _messages.value = _messages.value.plus(data)
                        }
                        else -> Unit
                    }
                }
                .catch {
                    Log.e("Error", "Error", it)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun validateMessage() = viewModelScope.launch {

    }

    @AssistedFactory
    fun interface ChatBoxViewModelFactory {
        fun create(chatId: Long): ChatBoxViewModel
    }
}