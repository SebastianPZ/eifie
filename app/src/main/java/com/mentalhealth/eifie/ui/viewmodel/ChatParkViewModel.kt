package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.usecases.RetrieveSupporterUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserChatsUseCase
import com.mentalhealth.eifie.domain.usecases.SaveChatUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.mappers.impl.ChatUIMapper
import com.mentalhealth.eifie.ui.models.ChatUI
import com.mentalhealth.eifie.util.userPreferences
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
    private val preferences: EPreferences,
    private val getUserChatsUseCase: GetUserChatsUseCase,
    private val saveChatUseCase: SaveChatUseCase,
    private val retrieveSupporterUseCase: RetrieveSupporterUseCase
): LazyViewModel() {

    private val _chatsHistory: MutableStateFlow<List<ChatUI>> = MutableStateFlow(listOf())

    val chatsHistory = _chatsHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _supporter: MutableStateFlow<Supporter> = MutableStateFlow(Supporter())

    val supporter = _supporter.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Supporter()
    )

    init {
        initChatsData()
    }

    private fun initChatsData() = viewModelScope.launch {
        initChats().join()
    }

    fun saveSupporter() = viewModelScope.launch {
        retrieveSupporterUseCase.invoke()
            .onStart { viewState.value = ViewState.Success }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ViewState.Success
                    is EResult.Success -> result.run {
                        _supporter.value = data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    fun saveChat() = viewModelScope.launch {
        saveChatUseCase.invoke(_supporter.value)
            .onStart { viewState.value = ViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ViewState.Success
                    is EResult.Success -> initChats()
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    private fun initChats() = viewModelScope.launch {
        getUserChatsUseCase.invoke(preferences.readPreference(userPreferences) ?: 0)
            .onStart { viewState.value = ViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ViewState.Success
                    is EResult.Success -> result.run {
                        _chatsHistory.value = data.map {
                            ChatUIMapper.mapFromEntity(it)
                        }
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

}