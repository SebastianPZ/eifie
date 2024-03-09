package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.util.manager.CalendarManager
import kotlinx.coroutines.flow.flow

class GetMonthCalendarUseCase {

    fun invoke() = flow {
        val info = CalendarManager().getMonthInfoOfMonth()
        emit(info)
    }

}