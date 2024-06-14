package com.mentalhealth.eifie.ui.viewmodel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.GetAppointmentNotificationsUseCase
import com.mentalhealth.eifie.domain.usecases.GetNotificationsUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.view.appointment.AppointmentsState
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
class HomeViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getAppointmentNotificationsUseCase: GetAppointmentNotificationsUseCase
): LazyViewModel() {

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

    private val _notifications: MutableStateFlow<List<Notification>> = MutableStateFlow(listOf())

    val notifications = _notifications.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _appointments: MutableStateFlow<List<Appointment>> = MutableStateFlow(listOf())

    val appointments = _appointments.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _appointmentsState: MutableStateFlow<AppointmentsState> = MutableStateFlow(AppointmentsState.Idle)

    val appointmentsState = _appointmentsState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = AppointmentsState.Idle
    )

    init {
        initUserCustomHome()
    }

    private fun initUserCustomHome() = viewModelScope.launch {
        getUserInformationUseCase.invoke()
            .onStart {
                viewState.value = ViewState.Loading
            }.onEach {
                when(it) {
                    is EResult.Success -> it.run { configUserHome(data).join() }
                    is EResult.Error -> viewState.value = ViewState.Error(it.error.message ?: "")
                }
            }.launchIn(viewModelScope)
    }

    private fun configUserHome(user: User) = viewModelScope.launch {
        _userRole.value = user.role
        _userName.value = user.firstName

        when(user.role) {
            Role.PATIENT -> getNotificationsUseCase().join()
            Role.PSYCHOLOGIST -> Unit
        }
    }

    private fun getNotificationsUseCase() = viewModelScope.launch {
        getNotificationsUseCase.invoke()
            .onStart {

            }.onEach {
                _notifications.value = it
            }.launchIn(viewModelScope)
    }

    fun getAppointmentNotifications(withPermission: Boolean = false) = viewModelScope.launch {
        if(_appointmentsState.value !is AppointmentsState.Success) {
            getAppointmentNotificationsUseCase.invoke(withPermission)
                .onStart {
                    _appointmentsState.value = AppointmentsState.Loading
                }.onEach {
                    when(it) {
                        is EResult.Error -> _appointmentsState.value = AppointmentsState.Error
                        is EResult.Success -> {
                            _appointments.value = it.data
                            _appointmentsState.value = AppointmentsState.Success
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun refreshAppointmentNotifications() = viewModelScope.launch {
        _appointmentsState.value = AppointmentsState.Idle
        getAppointmentNotifications(true).join()
    }

}