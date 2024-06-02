package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.CreateSupportBotUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserChatsUseCase
import com.mentalhealth.eifie.domain.usecases.SaveChatUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.mappers.impl.ChatUIMapper
import com.mentalhealth.eifie.ui.models.ChatUI
import com.mentalhealth.eifie.ui.view.chat.main.ChatViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatParkViewModel @Inject constructor(
    private val getUserChatsUseCase: GetUserChatsUseCase,
    private val saveChatUseCase: SaveChatUseCase,
    private val saveSupportBotUseCase: CreateSupportBotUseCase
): LazyViewModel() {

    private val _chatsHistory: MutableStateFlow<List<ChatUI>> = MutableStateFlow(listOf())

    val chatsHistory = _chatsHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initChats()
    }

    fun saveBot() = viewModelScope.launch {
        saveSupportBotUseCase.invoke("")
            .onStart { viewState.value = ChatViewState.Success }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ChatViewState.Success
                    is EResult.Success -> result.run {
                        saveChat(data.id ?: 0)
                        viewState.value = ChatViewState.Success
                    }
                }
            }
            .catch { viewState.value = ChatViewState.Error }
            .launchIn(viewModelScope)
    }

    private fun saveChat(supBot: Long) = viewModelScope.launch {
        saveChatUseCase.invoke(supBot)
            .onStart { viewState.value = ChatViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ChatViewState.Success
                    is EResult.Success -> initChats()
                }
            }
            .catch { viewState.value = ChatViewState.Error }
            .launchIn(viewModelScope)
    }

    private fun initChats() = viewModelScope.launch {
        getUserChatsUseCase.invoke()
            .onStart { viewState.value = ChatViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ChatViewState.Success
                    is EResult.Success -> result.run {
                        _chatsHistory.value = data.map {
                            ChatUIMapper.mapFromEntity(it)
                        }
                        viewState.value = ChatViewState.Success
                    }
                }
            }
            .catch { viewState.value = ChatViewState.Error }
            .launchIn(viewModelScope)
    }

}