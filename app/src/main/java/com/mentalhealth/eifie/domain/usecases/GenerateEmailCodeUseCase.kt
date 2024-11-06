package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateEmailCodeUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun invoke(email: String) = flow {
        emit(authenticationRepository.generateEmailCode(email))
    }
}