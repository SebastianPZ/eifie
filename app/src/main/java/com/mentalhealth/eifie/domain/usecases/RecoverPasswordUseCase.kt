package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecoverPasswordUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun invoke(email: String) = flow {
        emit(authenticationRepository.recoverPassword(email))
    }

}