package com.mentalhealth.eifie.ui.appointment.list

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.states.AppointmentListState
import com.mentalhealth.eifie.domain.usecases.GetMonthCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.GetWeekCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.ListAppointmentsUseCase
import com.mentalhealth.eifie.ui.appointment.calendar.CalendarViewState
import com.mentalhealth.eifie.ui.common.LazyViewModel
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
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getMonthCalendarUseCase: GetMonthCalendarUseCase,
    private val getWeekCalendarUseCase: GetWeekCalendarUseCase,
    private val listAppointmentsUseCase: ListAppointmentsUseCase
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

    private var appointmentJob: Job? = null

    init {
        initValues()
    }

    private fun initValues() = viewModelScope.launch{
        getWeekInformation().join()
        listAppointments().join()
    }

    fun updateCalendarState() {
        when(calendarState.value) {
            is CalendarViewState.Expanded -> {
                getWeekInformation()
            }
            is CalendarViewState.Condense -> {
                getMonthInformation()
            }
            else -> Unit
        }
    }

    val updateAppointmentsByDate: (date: Date) -> Unit = {
        listAppointments(it)
    }

    private fun getWeekInformation() = viewModelScope.launch {
        getWeekCalendarUseCase.invoke().onStart {
            calendarViewState.value = CalendarViewState.Loading
        }.onEach {
            calendarViewState.value = CalendarViewState.Condense(it.days)
        }.launchIn(viewModelScope)
    }

    private fun getMonthInformation() = viewModelScope.launch {
        getMonthCalendarUseCase.invoke().onStart {
            calendarViewState.value = CalendarViewState.Loading
        }.onEach {
            calendarViewState.value = CalendarViewState.Expanded(it.days)
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