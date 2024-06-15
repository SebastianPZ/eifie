package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.util.getCompleteDate
import com.mentalhealth.eifie.util.getFormattedDate
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class GetAppointmentNotificationsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) {

    fun invoke(withPermission: Boolean) = flow {

        val appointmentEResult = when(val result = userRepository.getUser()) {
            is EResult.Success -> result.data.run {

                val timeZone = TimeZone.getTimeZone("America/Lima")
                val locale = Locale("es", "PE")
                val calendar = Calendar.getInstance(timeZone, locale)


                val startDate = calendar.time.getFormattedDate()
                val endDate = calendar.apply { add(Calendar.DATE, 5) }.time.getFormattedDate()

                val appointmentsResult = when(role) {
                    Role.PATIENT -> appointmentRepository.getAppointmentsByPatient(
                        uid,
                        startDate,
                        endDate
                    )
                    Role.PSYCHOLOGIST -> appointmentRepository.getAppointmentsByPsychologist(
                        uid,
                        startDate,
                        endDate
                    )
                }

                when (appointmentsResult) {
                    is EResult.Error -> EResult.Error(appointmentsResult.error)
                    is EResult.Success -> {
                        appointmentsResult.data
                            .filter { getCompleteDate(it.date, it.time)?.after(Date()) ?: false }
                            .run {
                                if (withPermission) notificationRepository.save(
                                    this.map {
                                        Notification(
                                            key = "${it.appointmentId}",
                                            date = getCompleteDate(it.date, it.time)
                                        )
                                    }
                                )
                                EResult.Success(this)
                            }
                    }
                }
            }
            is EResult.Error -> result
        }
        emit(appointmentEResult)
    }

}