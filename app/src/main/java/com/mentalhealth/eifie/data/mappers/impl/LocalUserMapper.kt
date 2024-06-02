package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalHospital
import com.mentalhealth.eifie.data.local.database.entities.LocalUser
import com.mentalhealth.eifie.data.local.database.entities.getRole
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.User

object LocalUserMapper: Mapper<LocalUser, User> {
    override fun mapFromEntity(entity: User): LocalUser {
        return LocalUser(
            uid = entity.uid,
            profileId = entity.profileId,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            userName = entity.userName,
            birthDate = entity.birthDate,
            picture = entity.picture,
            role = entity.role.ordinal,
            psychologistAssigned = entity.psychologistAssigned,
            hospital = LocalHospital(
                hospitalId = entity.hospitalId,
                hospitalName = entity.hospitalName
            )
        )
    }

    override fun mapToEntity(model: LocalUser): User {
        return User(
            uid = model.uid,
            profileId = model.profileId,
            firstName = model.firstName ?: "",
            lastName = model.lastName ?: "",
            email = model.email ?: "",
            birthDate = model.birthDate ?: "",
            userName = model.userName ?: "",
            picture = model.picture,
            psychologistAssigned = model.psychologistAssigned,
            hospitalId = model.hospital?.hospitalId ?: 0,
            hospitalName = model.hospital?.hospitalName ?: "",
            role = model.getRole()
        )
    }
}