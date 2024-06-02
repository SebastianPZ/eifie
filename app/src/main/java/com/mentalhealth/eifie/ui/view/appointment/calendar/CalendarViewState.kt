package com.mentalhealth.eifie.ui.view.appointment.calendar

import com.mentalhealth.eifie.util.manager.DateInfo

sealed class CalendarViewState {
    object Idle: CalendarViewState()
    object Loading: CalendarViewState()
    data class Expanded(val days: List<DateInfo>, val month: String): CalendarViewState()
    data class Condense(val days: List<DateInfo>, val week: Int, val month: String): CalendarViewState()
    object Error: CalendarViewState()
}