package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.request.AppointmentRequest
import com.mentalhealth.eifie.domain.entities.states.RegisterResult
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.util.ERR_REGISTER
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {

    fun invoke(appointment: AppointmentRequest) = flow {
        emit(
            when(val result = appointmentRepository.saveAppointment(appointment = appointment)) {
                is DataResult.Success -> RegisterResult.Success
                is DataResult.Error -> result.run {
                    RegisterResult.Error(error.message ?: ERR_REGISTER)
                }
                else -> RegisterResult.Loading
            }
        )
    }

}