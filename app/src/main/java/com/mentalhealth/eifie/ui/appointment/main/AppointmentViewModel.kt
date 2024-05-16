package com.mentalhealth.eifie.ui.appointment.main

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.entities.states.AppointmentListState
import com.mentalhealth.eifie.domain.usecases.GetMonthCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.GetWeekCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.ListAppointmentsUseCase
import com.mentalhealth.eifie.ui.appointment.calendar.CalendarViewState
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.util.manager.DateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getMonthCalendarUseCase: GetMonthCalendarUseCase,
    private val getWeekCalendarUseCase: GetWeekCalendarUseCase,
    private val listAppointmentsUseCase: ListAppointmentsUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase
): LazyViewModel() {

    val daysHeader = listOf("lun", "mar", "mie", "jue", "vie", "sab", "dom")

    private val calendarViewState: MutableStateFlow<CalendarViewState> = MutableStateFlow(
        CalendarViewState.Idle
    )

    val calendarState = calendarViewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = CalendarViewState.Idle
    )

    private val _userRole: MutableStateFlow<Role> = MutableStateFlow(Role.PATIENT)

    val userRole = _userRole.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Role.PATIENT
    )

    private var appointmentJob: Job? = null

    init {
        initValues()
    }

    private fun initValues() = viewModelScope.launch {
        initUserData().join()
        getWeekInformation().join()
        listAppointments().join()
    }

    private fun initUserData() = viewModelScope.launch {
        getUserInformationUseCase.invoke()
            .onStart {

            }.onEach {
                when(it) {
                    is DataResult.Success -> it.run { configUserData(data) }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun configUserData(user: UserSession) {
        _userRole.value = user.role
    }

    fun updateCalendarState() {
        when(calendarState.value) {
            is CalendarViewState.Expanded -> {
                getWeekInformation()
            }
            is CalendarViewState.Condense -> (calendarState.value as CalendarViewState.Condense).run {
                getMonthInformation(this.days.find { it.isSelected }?.date)
            }
            else -> Unit
        }
    }

    val updateAppointmentsByDate: (date: DateInfo) -> Unit = {
        updateDaysOnCalendar(it.id)
        listAppointments(it.date)
    }

    private fun updateDaysOnCalendar(dateId: Int) {
        when(calendarViewState.value) {
            is CalendarViewState.Condense -> (calendarViewState.value as CalendarViewState.Condense).run {
                calendarViewState.value = this.copy(days = days.map { it.copy(isSelected = it.id == dateId && it.inCurrentMonth) })
            }
            is CalendarViewState.Expanded -> (calendarViewState.value as CalendarViewState.Expanded).run {
                calendarViewState.value = this.copy(days = days.map { it.copy(isSelected = it.id == dateId && it.inCurrentMonth) })
            }
            else -> Unit
        }
    }

    private fun getWeekInformation() = viewModelScope.launch {
        getWeekCalendarUseCase.invoke().onStart {
            calendarViewState.value = CalendarViewState.Loading
        }.onEach {weekInfo ->
            calendarViewState.value = CalendarViewState.Condense(
                weekInfo.days,
                weekInfo.weekId,
                weekInfo.month.replaceFirstChar { it.titlecase(Locale.getDefault()) })
        }.launchIn(viewModelScope)
    }

    private fun getMonthInformation(date: Date?) = viewModelScope.launch {
        getMonthCalendarUseCase.invoke(date).onStart {
            calendarViewState.value = CalendarViewState.Loading
        }.onEach { monthInfo ->
            calendarViewState.value = CalendarViewState.Expanded(
                monthInfo.days,
                monthInfo.month.replaceFirstChar { it.titlecase(Locale.getDefault()) })
        }.launchIn(viewModelScope)
    }

    private fun listAppointments(date: Date? = null) = viewModelScope.launch {
        appointmentJob?.cancelAndJoin()
        appointmentJob = listAppointmentsUseCase.invoke(date)
            .onStart {
                viewState.value = AppointmentViewState.Loading
            }.onEach {
                when(it) {
                    is AppointmentListState.Success -> it.run {
                        viewState.value = AppointmentViewState.Success(appointments)
                    }
                    is AppointmentListState.Error -> it.run {
                        viewState.value = AppointmentViewState.Error(message)
                    }
                    else -> viewState.value = AppointmentViewState.Loading
                }
            }.launchIn(viewModelScope)
    }

}