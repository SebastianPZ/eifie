package com.mentalhealth.eifie.domain.usecases

import androidx.compose.ui.graphics.Color
import com.mentalhealth.eifie.data.network.ApiException
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.response.AppointmentResponse
import com.mentalhealth.eifie.data.network.models.response.getAppointmentErrorMessage
import com.mentalhealth.eifie.domain.entities.models.AppointmentList
import com.mentalhealth.eifie.domain.entities.models.AppointmentStyle
import com.mentalhealth.eifie.domain.entities.states.AppointmentListState
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.models.UserAppointment
import com.mentalhealth.eifie.domain.entities.models.toDomain
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.SkyBlue
import com.mentalhealth.eifie.util.ERR_EMPTY
import com.mentalhealth.eifie.util.manager.CalendarManager
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class ListAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val userRepository: UserRepository,
    private val calendarManager: CalendarManager = CalendarManager()
) {
    fun invoke(date: Date? = null) = flow {
        val appointmentResult = when(val userResponse = userRepository.getUser()) {
            DataResult.Loading -> AppointmentListState.Loading
            is DataResult.Success -> userResponse.data.run {

                val startDate = date ?: calendarManager.today
                val endDate = calendarManager.getDateOfMonthRange(startDate)

                val result = when(role) {
                    Role.PATIENT.ordinal -> appointmentRepository.getAppointmentsByPatient(
                        uid,
                        calendarManager.getFormattedDate(startDate),
                        endDate
                    )
                    else -> appointmentRepository.getAppointmentsByPsychologist(
                        uid,
                        calendarManager.getFormattedDate(startDate),
                        endDate
                    )
                }
                handleAppointmentResponse(result, role ?: Role.PATIENT.ordinal)
            }
            is DataResult.Error -> userResponse.run {
                AppointmentListState.Error(error.message ?: ERR_EMPTY)
            }
        }
        emit(appointmentResult)
    }

    private fun handleAppointmentResponse(
        result: DataResult<List<AppointmentResponse>, Exception>,
        role: Int): AppointmentListState {
        return when(result) {
            DataResult.Loading -> AppointmentListState.Loading
            is DataResult.Success -> result.run {
                AppointmentListState.Success(handleAppointmentByDateResponse(data, role))
            }
            is DataResult.Error -> result.run{
                when(error) {
                    is ApiException -> AppointmentListState.Error(getAppointmentErrorMessage(error.errorCode))
                    else -> AppointmentListState.Error(error.message ?: "")
                }
            }
        }
    }

    private fun handleAppointmentByDateResponse(list: List<AppointmentResponse>, role: Int): UserAppointment {
        val today = calendarManager.getFormattedDate()
        val (todayList, rest) = list
            .partition { it.date == today }

        val datesOfWeek = calendarManager.getWeekDatesOfDate(today)
        val (weekList, soonList) = rest
            .filter { it !in todayList }
            .partition { it.date in datesOfWeek }

        return UserAppointment(
            appointments = listOf(
                AppointmentList(
                    type = AppointmentList.TODAY,
                    list = todayList.map { it.toDomain(role) },
                    style = AppointmentStyle("Citas de hoy", Color.White, Purple)
                ),
                AppointmentList(
                    type = AppointmentList.WEEK,
                    list = weekList.map { it.toDomain(role) },
                    style = AppointmentStyle("Citas de la semana", BlackGreen, SkyBlue)
                ),
                AppointmentList(
                    type = AppointmentList.SOON,
                    list = soonList.map { it.toDomain(role) },
                    style = AppointmentStyle("Próximas citas", BlackGreen, LightSkyGray)
                )
            )
        )
    }
}