package com.mentalhealth.eifie.util.manager

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class DateInfo(
    val day: String,
    val date: Date,
    val inCurrentMonth: Boolean = false,
    val isToday: Boolean = false
)


data class WeekInfo(
    val weekId: Int,
    val days: List<DateInfo>
)

data class MonthInfo(
    val monthId: Int,
    val month: String,
    val days: List<DateInfo>
)


class CalendarManager(private val date: Date = Date()) {
    private val timeZone = TimeZone.getTimeZone("America/Lima")
    private val locale = Locale("es", "PE")
    private val calendar = Calendar.getInstance(timeZone, locale)
    private val dayFormatter = SimpleDateFormat("dd", Locale.getDefault())

    init {
        calendar.time = date
    }

    fun getMonthInfoOfMonth(month: Int = calendar[Calendar.MONTH]): MonthInfo {
        val calendarInitial = Calendar.getInstance(timeZone, locale)
        calendarInitial[Calendar.MONTH] = month
        calendarInitial[Calendar.DAY_OF_MONTH] = 1
        val startWeek = calendarInitial[Calendar.WEEK_OF_YEAR]
        val totalWeeks = calendarInitial.getActualMaximum(Calendar.WEEK_OF_MONTH)
        val endWeek = startWeek + totalWeeks - 1

        val daysOfMonth = mutableListOf<DateInfo>()
        for (week: Int in startWeek..endWeek) {
            daysOfMonth.addAll(getWeekInfoOfMonthWeek(week, calendarInitial[Calendar.YEAR]).days)
        }

        return MonthInfo(calendar[Calendar.MONTH], "", daysOfMonth)
    }

    fun getWeekInfoOfDate(date: Date = this.date): WeekInfo {
        val week = calendar[Calendar.WEEK_OF_YEAR]
        val calendarWeek = Calendar.getInstance(timeZone, locale)
        calendarWeek[Calendar.DAY_OF_MONTH] = 1
        calendarWeek[Calendar.MONTH] = 0
        calendarWeek[Calendar.YEAR] = calendar[Calendar.YEAR]

        calendarWeek.add(Calendar.WEEK_OF_YEAR, week - 1)
        val startDate = calendarWeek.time

        calendarWeek.add(Calendar.DAY_OF_MONTH, 7)
        val endDate = calendarWeek.time

        val daysOfWeek = mutableListOf<DateInfo>()
        val calendarHelper = Calendar.getInstance(timeZone, locale)
        calendarHelper.time = startDate

        while(calendarHelper.time.before(endDate)) {
            daysOfWeek.add(
                DateInfo(
                    day = dayFormatter.format(calendarHelper.time),
                    date = calendarHelper.time,
                    inCurrentMonth = calendarHelper[Calendar.MONTH] == calendar[Calendar.MONTH],
                    isToday = calendarHelper.compareWith(calendar)
                )
            )
            calendarHelper.add(Calendar.DATE, 1)
        }

        return WeekInfo(calendarWeek[Calendar.WEEK_OF_MONTH], daysOfWeek)
    }

    private fun getWeekInfoOfMonthWeek(week: Int, year: Int): WeekInfo {
        val calendarWeek = Calendar.getInstance(timeZone, locale)
        calendarWeek[Calendar.MONTH] = 0
        calendarWeek[Calendar.DAY_OF_MONTH] = 1
        calendarWeek[Calendar.YEAR] = year

        calendarWeek.add(Calendar.WEEK_OF_YEAR, week - 1)

        val startDate = calendarWeek.time

        calendarWeek.add(Calendar.DAY_OF_MONTH, 7)
        val endDate = calendarWeek.time

        val daysOfWeek = mutableListOf<DateInfo>()
        val calendarHelper = Calendar.getInstance(timeZone, locale)
        calendarHelper.time = startDate

        while(calendarHelper.time.before(endDate)) {
            daysOfWeek.add(
                DateInfo(
                    day = dayFormatter.format(calendarHelper.time),
                    date = calendarHelper.time,
                    inCurrentMonth = calendarHelper[Calendar.MONTH] == calendar[Calendar.MONTH],
                    isToday = calendarHelper.compareWith(calendar)
                )
            )
            calendarHelper.add(Calendar.DATE, 1)
        }

        return WeekInfo(calendarWeek[Calendar.WEEK_OF_MONTH], daysOfWeek)
    }

    private fun Calendar.compareWith(calendarToCompare: Calendar): Boolean {
        return this[Calendar.DAY_OF_MONTH] == calendarToCompare[Calendar.DAY_OF_MONTH] &&
                this[Calendar.MONTH] == calendarToCompare[Calendar.MONTH]
    }


}