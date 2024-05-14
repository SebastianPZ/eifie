package com.mentalhealth.eifie.ui.chat.main

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.usecases.GetUserChatsUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserChatsUseCase: GetUserChatsUseCase
): LazyViewModel() {

    init {
        initChats()
    }

    private fun initChats() = viewModelScope.launch {
        getUserChatsUseCase.invoke()
            .onStart { viewState.value = ChatViewState.Loading }
            .onEach {
                viewState.value = ChatViewState.Success(chats = it)
            }
            .catch { viewState.value = ChatViewState.Error }
            .launchIn(viewModelScope)
    }

}