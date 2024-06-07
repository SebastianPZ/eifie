package com.mentalhealth.eifie.domain.usecases

import androidx.compose.ui.graphics.Color
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentList
import com.mentalhealth.eifie.domain.entities.AppointmentStyle
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.UserAppointment
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.SkyBlue
import com.mentalhealth.eifie.util.compareWith
import com.mentalhealth.eifie.util.manager.CalendarManager
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class ListAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val userRepository: UserRepository
) {
    fun invoke(date: Date? = null) = flow {

        val calendarManager = CalendarManager(date = date ?: Date())

        val appointmentEResult = when(val result = userRepository.getUser()) {
            is EResult.Success -> result.data.run {

                val startDate = date ?: calendarManager.today
                val endDate = calendarManager.getDateOfMonthRange(startDate)

                val appointmentsResult = when(role) {
                    Role.PATIENT -> appointmentRepository.getAppointmentsByPatient(
                        uid,
                        calendarManager.getFormattedDate(startDate),
                        endDate
                    )
                    Role.PSYCHOLOGIST -> appointmentRepository.getAppointmentsByPsychologist(
                        uid,
                        calendarManager.getFormattedDate(startDate),
                        endDate
                    )
                }

                when(appointmentsResult) {
                    is EResult.Error -> EResult.Error(appointmentsResult.error)
                    is EResult.Success -> EResult.Success(handleAppointmentByDate(appointmentsResult.data, calendarManager))
                }
            }
            is EResult.Error -> result
        }
        emit(appointmentEResult)
    }

    private fun handleAppointmentByDate(list: List<Appointment>, calendarManager: CalendarManager): UserAppointment {
        val today = calendarManager.today
        val (todayList, rest) = list
            .partition {
                it.date.compareWith(today)
            }

        val datesOfWeek = calendarManager.getWeekDatesOfDate(today)
        val (weekList, soonList) = rest
            .filter { it !in todayList }
            .partition { it.date in datesOfWeek }

        return UserAppointment(
            appointments = listOf(
                AppointmentList(
                    type = AppointmentList.TODAY,
                    list = todayList,
                    style = AppointmentStyle("Citas de hoy", Color.White, Purple)
                ),
                AppointmentList(
                    type = AppointmentList.WEEK,
                    list = weekList,
                    style = AppointmentStyle("Citas de la semana", BlackGreen, SkyBlue)
                ),
                AppointmentList(
                    type = AppointmentList.SOON,
                    list = soonList,
                    style = AppointmentStyle("Próximas citas", BlackGreen, LightSkyGray)
                )
            )
        )
    }
}