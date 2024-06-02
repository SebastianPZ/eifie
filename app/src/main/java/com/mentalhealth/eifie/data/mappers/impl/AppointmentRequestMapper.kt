package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.request.AppointmentRequest
import com.mentalhealth.eifie.domain.entities.AppointmentParams

object AppointmentRequestMapper: Mapper<AppointmentRequest, AppointmentParams> {
    override fun mapFromEntity(entity: AppointmentParams): AppointmentRequest {
        return AppointmentRequest(
            patientId = entity.patientId,
            psychologistId = entity.psychologistId,
            date = entity.date,
            time = entity.time
        )
    }

    override fun mapToEntity(model: AppointmentRequest): AppointmentParams {
        TODO("Not yet implemented")
    }
}