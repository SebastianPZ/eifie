package com.mentalhealth.eifie.ui.appointment

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.usecases.GetMonthCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.GetWeekCalendarUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
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
class AppointmentViewModel @Inject constructor(
    private val getMonthCalendarUseCase: GetMonthCalendarUseCase,
    private val getWeekCalendarUseCase: GetWeekCalendarUseCase
): LazyViewModel() {

    private val calendarViewState: MutableStateFlow<CalendarViewState> = MutableStateFlow(CalendarViewState.Idle)

    val calendarState = calendarViewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = CalendarViewState.Idle
    )

    init {
        getWeekInformation()
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

    private fun getWeekInformation() {
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

    private fun getAppointmentsList() {

    }

}