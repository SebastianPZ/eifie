package com.mentalhealth.eifie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.ui.common.navigationbar.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val navigationItems = listOf(
        NavigationItem.HOME,
        NavigationItem.APPOINTMENT,
        NavigationItem.PROFILE
    )

    private val selectedItem = MutableStateFlow(navigationItems.first())

    val item = selectedItem.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = navigationItems.first()
    )

    fun updateSelectedItem(item: NavigationItem) {
        selectedItem.value = item
    }

}