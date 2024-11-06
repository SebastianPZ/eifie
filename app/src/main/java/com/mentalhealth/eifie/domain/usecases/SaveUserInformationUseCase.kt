package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserInformationUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun invoke(user: User, password: String) = flow {
        emit(repository.saveUser(user, password))
    }

}