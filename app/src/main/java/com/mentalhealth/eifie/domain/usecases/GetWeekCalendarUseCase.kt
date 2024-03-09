package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.util.manager.CalendarManager
import kotlinx.coroutines.flow.flow

class GetWeekCalendarUseCase {

    fun invoke() = flow {
        val info = CalendarManager().getWeekInfoOfDate()
        emit(info)
    }

}