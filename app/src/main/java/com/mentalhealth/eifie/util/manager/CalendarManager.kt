package com.mentalhealth.eifie.util.manager

import com.mentalhealth.eifie.util.compareWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class DateInfo(
    val id: Int,
    val day: String,
    val date: Date,
    val inCurrentMonth: Boolean = false,
    val isToday: Boolean = false,
    var isSelected: Boolean = false,
)


data class WeekInfo(
    val weekId: Int,
    val month: String,
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
    val calendar = Calendar.getInstance(timeZone, locale)

    init {
        calendar.time = date
    }

    val today: Date get() = calendar.time

    fun getFormattedDate(date: Date = today): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", locale)
        return dateFormatter.format(date)
    }

    fun getDateOfMonthRange(date: Date, monthRange: Int = 3): String {
        val calendarHelper = Calendar.getInstance(timeZone, locale)
        calendarHelper.time = date
        calendarHelper.add(Calendar.MONTH, monthRange)

        return getFormattedDate(calendarHelper.time)
    }

    fun getWeekDatesOfDate(date: Date): List<Date> {
        val calendarHelper = Calendar.getInstance(timeZone, locale)
        calendarHelper.time = date

        val weekInfo = getWeekInfoOfDate(calendar.time)

        return weekInfo.days.map {
            it.date
        }
    }

    fun getMonthInfoOfMonth(month: Int = calendar[Calendar.MONTH], date: Date = this.date): MonthInfo {
        val calendarInitial = Calendar.getInstance(timeZone, locale)
        calendarInitial[Calendar.MONTH] = month
        calendarInitial[Calendar.DAY_OF_MONTH] = 1
        val startWeek = calendarInitial[Calendar.WEEK_OF_YEAR]
        val totalWeeks = calendarInitial.getActualMaximum(Calendar.WEEK_OF_MONTH)
        val endWeek = startWeek + totalWeeks - 1

        val daysOfMonth = mutableListOf<DateInfo>()
        for (week: Int in startWeek..endWeek) {
            daysOfMonth.addAll(getWeekInfoOfMonthWeek(week, calendarInitial[Calendar.YEAR], date).days)
        }

        val monthFormatter = SimpleDateFormat("MMMM", locale)
        return MonthInfo(calendar[Calendar.MONTH], monthFormatter.format(date), daysOfMonth)
    }

    fun getWeekInfoOfDate(date: Date = this.date): WeekInfo {
        val calendarWeek = Calendar.getInstance(timeZone, locale)
        calendarWeek.time = date
        val week = calendarWeek[Calendar.WEEK_OF_YEAR]
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


        val dayFormatter = SimpleDateFormat("dd", locale)
        while(calendarHelper.time.before(endDate)) {
            daysOfWeek.add(
                DateInfo(
                    id = calendarHelper[Calendar.DAY_OF_MONTH],
                    day = dayFormatter.format(calendarHelper.time),
                    date = calendarHelper.time,
                    inCurrentMonth = calendarHelper[Calendar.MONTH] == calendar[Calendar.MONTH],
                    isToday = calendarHelper.time.compareWith(calendar.time)
                ).apply { isSelected = isToday }
            )
            calendarHelper.add(Calendar.DATE, 1)
        }

        val monthFormatter = SimpleDateFormat("MMMM", locale)
        return WeekInfo(calendarWeek[Calendar.WEEK_OF_MONTH], monthFormatter.format(date), daysOfWeek)
    }

    private fun getWeekInfoOfMonthWeek(week: Int, year: Int, date: Date = this.date): WeekInfo {
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

        val dayFormatter = SimpleDateFormat("dd", locale)
        while(calendarHelper.time.before(endDate)) {
            daysOfWeek.add(
                DateInfo(
                    id = calendarHelper[Calendar.DAY_OF_MONTH],
                    day = dayFormatter.format(calendarHelper.time),
                    date = calendarHelper.time,
                    inCurrentMonth = calendarHelper[Calendar.MONTH] == calendar[Calendar.MONTH],
                    isToday = calendarHelper.time.compareWith(date)
                ).apply {
                    isSelected = isToday && inCurrentMonth
                }
            )
            calendarHelper.add(Calendar.DATE, 1)
        }

        return WeekInfo(calendarWeek[Calendar.WEEK_OF_MONTH], "", daysOfWeek)
    }

}