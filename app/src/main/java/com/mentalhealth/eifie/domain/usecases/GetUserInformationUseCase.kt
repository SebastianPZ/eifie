package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.entities.models.calculateAge
import com.mentalhealth.eifie.domain.entities.models.getRole
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.util.NO_INFO
import com.mentalhealth.eifie.util.PENDANT
import com.mentalhealth.eifie.util.ifBlank
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val repository: UserRepository
) {

    fun invoke() = flow {

        val result = when(val response = repository.getUser()) {
            is DataResult.Success -> response.data.run {
                DataResult.Success(
                    UserSession(
                        uid = this.uid,
                        profileId = this.profileId,
                        firstName = this.firstName ?: "",
                        lastName = this.lastName ?: "",
                        email = this.email ?: "",
                        birthDate = this.birthDate ?: "",
                        userName = this.userName ?: "",
                        picture = this.picture,
                        hospitalId = this.hospital?.hospitalId ?: 0,
                        hospitalName = this.hospital?.hospitalName.ifBlank { PENDANT },
                        role = this.getRole()
                    )
                )
            }

            is DataResult.Error -> response.run { this }
            DataResult.Loading -> DataResult.Loading
        }

        emit(result)
    }

}