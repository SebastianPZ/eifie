package com.mentalhealth.eifie.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

abstract class LazyViewModel : ViewModel() {

    protected val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)

    val state = viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ViewState.Idle
    )

}