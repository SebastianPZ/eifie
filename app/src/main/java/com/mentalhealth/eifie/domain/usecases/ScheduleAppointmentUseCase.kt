package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.AppointmentParams
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {

    fun invoke(appointment: AppointmentParams) = flow {
        emit(appointmentRepository.saveAppointment(appointment = appointment))
    }

}