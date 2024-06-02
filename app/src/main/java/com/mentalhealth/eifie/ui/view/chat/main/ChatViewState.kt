package com.mentalhealth.eifie.ui.view.chat.main

import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.models.ChatUI

sealed class ChatViewState: ViewState() {
    object Idle: ChatViewState()
    object Loading: ChatViewState()
    object Success: ChatViewState()
    object Error: ChatViewState()
}