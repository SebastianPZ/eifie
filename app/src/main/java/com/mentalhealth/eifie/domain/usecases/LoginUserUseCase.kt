package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.api.ApiException
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.request.LoginRequest
import com.mentalhealth.eifie.data.api.models.response.getErrorMessage
import com.mentalhealth.eifie.domain.entities.models.LoginStatus
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.entities.models.calculateAge
import com.mentalhealth.eifie.domain.entities.models.getRole
import com.mentalhealth.eifie.domain.entities.models.getUserName
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthenticationRepository) {

    fun invoke(request: LoginRequest) = flow {

        emit(LoginStatus.Loading)

        val result = when(val response = repository.authenticateUser(request)) {
            DataResult.Loading -> LoginStatus.Loading
            is DataResult.Success -> response.data.run {
                LoginStatus.Success(
                    UserSession(
                        uid = patientId ?: psychologistId ?: 0,
                        profileId = user?.userId ?: 0,
                        firstName = user?.firstName ?: "",
                        lastName = user?.lastName ?: "",
                        email = user?.email ?: "",
                        userName = getUserName(user?.firstName ?: "", user?.lastName ?: ""),
                        birthDate = user?.birthDate ?: "",
                        hospitalId = hospital?.hospitalId ?: 0,
                        hospitalName = hospital?.name ?: "",
                        picture = user?.picture?.url,
                        role = this.getRole()
                    )
                )
            }
            is DataResult.Error -> response.run {
                when(error) {
                    is ApiException -> LoginStatus.Error(getErrorMessage(error.errorCode))
                    else -> LoginStatus.Error(error.message ?: "")
                }
            }
        }

        emit(result)
    }

}