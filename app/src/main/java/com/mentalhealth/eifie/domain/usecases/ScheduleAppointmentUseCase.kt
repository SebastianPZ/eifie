package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.AppointmentParams
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val userRepository: UserRepository
) {

    fun invoke(appointment: AppointmentParams) = flow {
        userRepository.getUser().let {
            when(it) {
                is EResult.Error -> { emit(it) }
                is EResult.Success -> it.run {
                    emit(appointmentRepository.saveAppointment(appointment = appointment.apply {
                        psychologistId = data.uid
                    }))
                }
            }
        }
    }

}