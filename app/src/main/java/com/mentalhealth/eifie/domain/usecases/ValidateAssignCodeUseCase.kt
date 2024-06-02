package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateAssignCodeUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun invoke(accessCode: String) = flow {
        emit(repository.validatePsychologistCode(accessCode))
    }

}