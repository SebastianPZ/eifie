package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserInformationUseCase @Inject constructor(
    private val repository: UserRepository
)  {

    fun invoke(user: User) = flow {
        emit(repository.updateUser(user))
    }

}