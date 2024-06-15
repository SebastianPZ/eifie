package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun invoke(token: String) = flow {
        emit(userRepository.updateFirebaseToken(token))
    }
}