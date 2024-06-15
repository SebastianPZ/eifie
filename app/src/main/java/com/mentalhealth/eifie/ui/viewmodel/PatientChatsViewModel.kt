package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.GetPatientChatsUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.mappers.impl.ChatUIMapper
import com.mentalhealth.eifie.ui.models.ChatUI
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

@HiltViewModel(assistedFactory = PatientChatsViewModel.PatientChatsViewModelFactory::class)
class PatientChatsViewModel @AssistedInject constructor(
    @Assisted val patient: Long,
    private val getPatientChatsUseCase: GetPatientChatsUseCase
) : LazyViewModel() {

    private val _chats: MutableStateFlow<List<ChatUI>> = MutableStateFlow(listOf())

    val chats = _chats.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initPatientChats()
    }

    private fun initPatientChats() = viewModelScope.launch {
        getPatientChatsUseCase.invoke(patient)
            .onStart { viewState.value = ViewState.Loading }
            .onEach { result ->
                when(result) {
                    is EResult.Error -> viewState.value = ViewState.Success
                    is EResult.Success -> result.run {
                        _chats.value = data.map {
                            ChatUIMapper.mapFromEntity(it)
                        }
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch {
                viewState.value = ViewState.Error(it.message ?: "")
            }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    fun interface PatientChatsViewModelFactory {
        fun create(patient: Long): PatientChatsViewModel
    }
}