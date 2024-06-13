package com.mentalhealth.eifie.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.models.request.OpenAIRole
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.GetChatMessagesUseCase
import com.mentalhealth.eifie.domain.usecases.SendMessageUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.mappers.impl.MessageUIMapper
import com.mentalhealth.eifie.ui.models.MessageUI
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

@HiltViewModel(assistedFactory = ChatViewModel.ChatBoxViewModelFactory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted private val chatId: Long,
    private val getChatMessages: GetChatMessagesUseCase,
    private val sendMessage: SendMessageUseCase
): LazyViewModel() {

    private val _messages: MutableStateFlow<List<MessageUI>> = MutableStateFlow(listOf())

    val messages = _messages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initChatMessages()
    }

    private fun initChatMessages() = viewModelScope.launch {
        getChatMessages.invoke(chatId = chatId)
            .onStart { viewState.value = ViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Success -> result.run {
                        _messages.value = this.data.filter { it.role != OpenAIRole.SYSTEM.key  }.map {
                            MessageUIMapper.mapFromEntity(it)
                        }
                    }
                    is EResult.Error -> result.run { viewState.value = ViewState.Error(error.message ?: "") }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    fun sendMessage(message: String) {
        operateMessage(text = message)
    }

    private fun operateMessage(text: String) = viewModelScope.launch {
        MessageUI(chat = chatId, content = text, isFromMe = true).run {
            _messages.value = _messages.value.plus(this)

            sendMessage.invoke(MessageUIMapper.mapToEntity(this))
                .onStart {  }
                .onEach {
                    when(it) {
                        is EResult.Success -> it.data.run {
                            _messages.value = _messages.value.plus(MessageUIMapper.mapFromEntity(this))
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

    @AssistedFactory
    fun interface ChatBoxViewModelFactory {
        fun create(chatId: Long): ChatViewModel
    }
}