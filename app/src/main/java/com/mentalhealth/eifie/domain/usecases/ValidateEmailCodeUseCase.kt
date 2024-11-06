package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateEmailCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun invoke(code: String) = flow {
        emit(authenticationRepository.validateEmailCode(code))
    }
}