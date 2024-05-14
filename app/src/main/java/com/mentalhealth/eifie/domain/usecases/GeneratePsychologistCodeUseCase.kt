package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeneratePsychologistCodeUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    fun invoke(psychologistId: Long) = flow {
        emit(repository.generatePsychologistCode(psychologistId = psychologistId))
    }
}