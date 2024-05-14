package com.mentalhealth.eifie.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.ui.common.navigationbar.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor() : ViewModel() {

    val navigationItems by lazy { listOf(
        NavigationItem.HOME,
        NavigationItem.CHAT,
        NavigationItem.APPOINTMENT,
        NavigationItem.PROFILE
    ) }

    private val selectedItem = MutableStateFlow(NavigationItem.HOME)

    val item = selectedItem.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = NavigationItem.HOME
    )

    init {
        updateSelectedItem(navigationItems.first())
    }

    fun updateSelectedItem(item: NavigationItem) {
        selectedItem.value = item
    }

}