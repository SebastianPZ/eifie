package com.mentalhealth.eifie.ui.chat.chatbox

import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.ui.common.ViewState

sealed class ChatBoxViewState: ViewState() {
    object Loading: ChatBoxViewState()
    data class Success(val messages: List<Message>): ChatBoxViewState()
    object Error: ChatBoxViewState()
}