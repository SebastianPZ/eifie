package com.mentalhealth.eifie.ui.chat.main

import com.mentalhealth.eifie.domain.entities.models.Chat
import com.mentalhealth.eifie.ui.common.ViewState

sealed class ChatViewState: ViewState() {
    object Idle: ChatViewState()
    object Loading: ChatViewState()
    data class Success(val chats: List<Chat>): ChatViewState()
    object Error: ChatViewState()
}