package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    fun invoke() = flow {
        emit(repository.logoutUser())
    }
}