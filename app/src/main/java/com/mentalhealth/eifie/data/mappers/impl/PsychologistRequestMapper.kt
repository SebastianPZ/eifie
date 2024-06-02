package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.request.PsychologistRequest
import com.mentalhealth.eifie.domain.entities.PsychologistParams

object PsychologistRequestMapper: Mapper<PsychologistRequest, PsychologistParams> {
    override fun mapFromEntity(entity: PsychologistParams): PsychologistRequest {
        return PsychologistRequest(
            firstName = entity.firstName,
            lastName = entity.lastName,
            birthDate = entity.birthDate,
            email = entity.email,
            password = entity.password,
            hospitalId = entity.hospitalId
        )
    }

    override fun mapToEntity(model: PsychologistRequest): PsychologistParams {
        return PsychologistParams(
            firstName = model.firstName,
            lastName = model.lastName,
            birthDate = model.birthDate,
            email = model.email,
            password = model.password,
            hospitalId = model.hospitalId
        )
    }
}