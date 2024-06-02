package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.LoginResponse
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.util.getUserName

object LoginMapper: Mapper<LoginResponse, User> {
    override fun mapFromEntity(entity: User): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: LoginResponse): User {
        return User(
            uid = model.patientId ?: model.psychologistId ?: 0,
            profileId = model.user?.userId ?: 0,
            firstName = model.user?.firstName ?: "",
            lastName = model.user?.lastName ?: "",
            email = model.user?.email ?: "",
            userName = model.user?.let { getUserName(it.firstName ?: "", it.lastName ?: "") } ?: "",
            birthDate = model.user?.birthDate ?: "",
            hospitalId = model.hospital?.hospitalId ?: 0,
            hospitalName = model.hospital?.name ?: "",
            psychologistAssigned = model.psychologistAssignedId ?: -1,
            picture = model.user?.picture?.url,
            role = if (model.patientId != null) Role.PATIENT else Role.PSYCHOLOGIST
        )
    }
}