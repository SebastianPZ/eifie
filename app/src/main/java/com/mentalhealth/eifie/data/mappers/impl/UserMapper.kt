package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.UserResponse
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.util.getUserName

object UserMapper: Mapper<UserResponse, User> {
    override fun mapFromEntity(entity: User): UserResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: UserResponse): User {
        return User(
            profileId = model.userId ?: 0,
            firstName = model.firstName ?: "",
            lastName = model.lastName ?: "",
            email = model.email ?: "",
            birthDate = model.birthDate ?: "",
            userName = getUserName(model.firstName ?: "", model.lastName ?: ""),
            picture = model.picture?.url ?: ""
        )
    }
}