package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.database.entities.Hospital
import com.mentalhealth.eifie.data.database.entities.User
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserInformationUseCase @Inject constructor(
    private val repository: UserRepository
)  {

    fun invoke(userSession: UserSession) = flow {

        val result = repository.updateUser(
            User(
                uid = userSession.uid,
                profileId = userSession.profileId,
                firstName = userSession.firstName,
                lastName = userSession.lastName,
                userName = userSession.userName,
                email = userSession.email,
                birthDate = userSession.birthDate,
                picture = userSession.picture,
                role = userSession.role.ordinal,
                hospital = Hospital(
                    hospitalId = userSession.hospitalId,
                    hospitalName = userSession.hospitalName
                )
            )
        )

        emit(result)
    }

}