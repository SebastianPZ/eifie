package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.util.getCompleteDate
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class GetPatientAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    fun invoke(patient: Long) = flow {
        val endDate = Date()
        val locale = Locale("es", "PE")
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Lima"), locale)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", locale)
        calendar[Calendar.DAY_OF_YEAR] = 1
        calendar[Calendar.MONTH] = 6
        calendar[Calendar.YEAR] = 2024

        val startDate = calendar.time

        when (val result = appointmentRepository.getAppointmentsByPatient(
            patient,
            dateFormatter.format(startDate),
            dateFormatter.format(endDate)
        )) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(
                EResult.Success(result.data.filter {
                    getCompleteDate(
                        it.date,
                        it.time
                    )?.before(Date()) ?: false
                })
            )
        }
    }
}