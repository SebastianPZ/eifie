package com.mentalhealth.eifie.ui.view.chat.chatbox

import com.mentalhealth.eifie.domain.entities.Message
import com.mentalhealth.eifie.ui.common.ViewState

sealed class ChatBoxViewState: ViewState() {
    object Loading: ChatBoxViewState()
    data class Success(val messages: List<Message>): ChatBoxViewState()
    object Error: ChatBoxViewState()
}