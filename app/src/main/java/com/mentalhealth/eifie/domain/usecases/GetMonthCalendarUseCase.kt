package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.util.manager.CalendarManager
import kotlinx.coroutines.flow.flow
import java.util.Date

class GetMonthCalendarUseCase {

    fun invoke(date: Date?) = flow {
        val monthInfo = if(date != null) CalendarManager().getMonthInfoOfMonth(date = date)
                        else CalendarManager().getMonthInfoOfMonth()
        emit(monthInfo)
    }

}