package com.mentalhealth.eifie.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.ui.common.navigationbar.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
) : ViewModel() {

    private var role: Role = Role.PATIENT
    var navigationItems = mutableStateOf(listOf<NavigationItem>())
        private set


    private val selectedItem = MutableStateFlow(NavigationItem.HOME)

    val item = selectedItem.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = NavigationItem.HOME
    )

    init {
        initUserCustomHome()
    }


    private fun initUserCustomHome() = viewModelScope.launch {
        getUserInformationUseCase.invoke()
            .onStart {

            }.onEach {
                when(it) {
                    is EResult.Success -> it.run {
                        role = data.role
                        initNavigationItems()
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun initNavigationItems() {
        navigationItems.value = when(role) {
            Role.PSYCHOLOGIST -> {
                listOf(
                    NavigationItem.HOME,
                    NavigationItem.PATIENTS,
                    NavigationItem.APPOINTMENT,
                    NavigationItem.PROFILE)
            }
            else -> {
                listOf(
                    NavigationItem.HOME,
                    NavigationItem.CHAT,
                    NavigationItem.APPOINTMENT,
                    NavigationItem.PROFILE)
            }
        }
        updateSelectedItem(navigationItems.value.first())
    }

    fun updateSelectedItem(item: NavigationItem) {
        selectedItem.value = item
    }

}