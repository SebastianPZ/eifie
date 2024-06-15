package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AppRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val appRepository: AppRepository
) {

    fun invoke() = flow {
        when(val result = repository.logoutUser()) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(appRepository.clearAllData())
        }
    }
}