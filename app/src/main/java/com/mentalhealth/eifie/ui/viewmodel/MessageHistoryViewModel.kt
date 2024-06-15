package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.models.request.OpenAIRole
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.GetPatientMessageUseCase
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = MessageHistoryViewModel.MessageHistoryViewModelFactory::class)
class MessageHistoryViewModel @AssistedInject constructor(
    @Assisted("chat") private val chat: Long,
    @Assisted("patient") private val patient: Long,
    private val getPatientMessageUseCase: GetPatientMessageUseCase
): LazyViewModel() {

    private val _messages: MutableStateFlow<List<MessageUI>> = MutableStateFlow(listOf())

    val messages = _messages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initMessages()
    }

    private fun initMessages() = viewModelScope.launch {
        getPatientMessageUseCase.invoke(chat, patient)
            .onEach { viewState.value = ViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ViewState.Error(result.error.message ?: "")
                    is EResult.Success -> result.run {
                        _messages.value = this.data.filter { it.role != OpenAIRole.SYSTEM.key  }.map {
                            MessageUIMapper.mapFromEntity(it)
                        }
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    fun interface MessageHistoryViewModelFactory {
        fun create(@Assisted("chat") chat: Long, @Assisted("patient") patient: Long): MessageHistoryViewModel
    }
}