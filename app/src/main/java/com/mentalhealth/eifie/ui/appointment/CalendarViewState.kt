package com.mentalhealth.eifie.ui.appointment

import com.mentalhealth.eifie.util.manager.DateInfo

sealed class CalendarViewState {
    object Idle: CalendarViewState()
    object Loading: CalendarViewState()
    data class Expanded(val days: List<DateInfo>): CalendarViewState()
    data class Condense(val days: List<DateInfo>): CalendarViewState()
    object Error: CalendarViewState()
}