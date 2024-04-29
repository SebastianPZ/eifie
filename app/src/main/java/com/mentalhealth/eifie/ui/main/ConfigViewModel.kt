package com.mentalhealth.eifie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.ui.appointment.calendar.CalendarViewState
import com.mentalhealth.eifie.ui.common.navigationbar.NavigationItem
import com.mentalhealth.eifie.util.emptyString
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
class ConfigViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase
) : ViewModel() {

    private val _userRole: MutableStateFlow<Role> = MutableStateFlow(Role.PATIENT)

    val userRole = _userRole.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Role.PATIENT
    )

    private val _userName: MutableStateFlow<String> = MutableStateFlow(emptyString())

    val userName = _userName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = emptyString()
    )

    val navigationItems = listOf(
        NavigationItem.HOME,
        NavigationItem.APPOINTMENT,
        NavigationItem.PROFILE
    )

    init {
        initUserConfiguration()
    }

    private fun initUserConfiguration() = viewModelScope.launch {
        getUserInformationUseCase.invoke()
            .onStart {

            }.onEach {
                when(it) {
                    is DataResult.Success -> it.run { configUserApp(data) }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun configUserApp(user: UserSession) {
        _userRole.value = user.role
        _userName.value = user.firstName
    }


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