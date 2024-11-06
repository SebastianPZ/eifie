package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.UpdatePasswordRequest
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {
    fun invoke(actualPassword: String, newPassword: String) = flow<EResult<Boolean, Exception>> {
        emit(
            userRepository.getUser().let { userResult ->
                when(userResult) {
                    is EResult.Success -> { authenticationRepository.updatePassword(
                        UpdatePasswordRequest(
                            email = userResult.data.email,
                            actualPassword = actualPassword,
                            newPassword = newPassword
                        )
                    ) }
                    is EResult.Error -> { userResult }
                }
            }
        )
    }
}