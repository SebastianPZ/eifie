package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AssignPsychologistUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    fun invoke(patientId: Long, psychologist: Long) = flow {
        emit(repository.assignPsychologist(patientId, psychologist))
    }
}