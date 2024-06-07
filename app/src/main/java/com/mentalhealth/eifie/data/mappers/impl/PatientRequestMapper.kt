package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.request.PatientRequest
import com.mentalhealth.eifie.domain.entities.PatientParams

object PatientRequestMapper: Mapper<PatientRequest, PatientParams> {
    override fun mapFromEntity(entity: PatientParams): PatientRequest {
        return PatientRequest(
            firstName = entity.firstName,
            lastName = entity.lastName,
            birthDate = entity.birthDate,
            email = entity.email,
            password = entity.password,
            psychologistId = entity.psychologistId
        )
    }

    override fun mapToEntity(model: PatientRequest): PatientParams {
        TODO("Not yet implemented")
    }

}