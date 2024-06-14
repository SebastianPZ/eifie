package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentParams
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class ScheduleAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) {

    fun invoke(appointment: AppointmentParams) = flow {
        userRepository.getUser().let {
            when(it) {
                is EResult.Error -> { emit(it) }
                is EResult.Success -> it.run {
                    when(val result = appointmentRepository.saveAppointment(appointment.apply {
                        psychologistId = data.uid
                    })) {
                        is EResult.Error -> emit(result)
                        is EResult.Success -> result.run {
                            saveNotification(data).run {
                                emit(result)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun saveNotification(appointment: Appointment) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Lima"), Locale("es", "PE"))

        if(appointment.date.before(calendar.apply { add(Calendar.DAY_OF_YEAR, 5) }.time)) {
            notificationRepository.save(listOf(
                Notification(
                    key = "${appointment.appointmentId}",
                    date = getCompleteDate(appointment.date, appointment.time)
                )
            ))
        }
    }

    private fun getCompleteDate(date: Date, time: String): Date? {
        val dateString = SimpleDateFormat("yyyy-MM-dd", Locale("es", "PE")).format(date)
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("es", "PE")).parse("$dateString $time")
    }

}