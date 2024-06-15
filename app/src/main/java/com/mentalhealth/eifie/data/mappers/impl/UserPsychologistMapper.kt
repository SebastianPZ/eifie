package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.UserPsychologistResponse
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.util.getUserName

object UserPsychologistMapper: Mapper<UserPsychologistResponse, Psychologist> {
    override fun mapFromEntity(entity: Psychologist): UserPsychologistResponse {
        return UserPsychologistResponse(

        )
    }

    override fun mapToEntity(model: UserPsychologistResponse): Psychologist {
        return Psychologist(
            id = model.psychologistId ?: 0L,
            uId = model.user?.userId ?: 0L,
            firstName = model.user?.firstName ?: "",
            lastName = model.user?.lastName ?: "",
            hospital = model.hospital?.name ?: "",
            birthDate = model.user?.birthDate ?: "",
            email = model.user?.email ?: "",
            picture = model.user?.picture?.url
        )
    }
}