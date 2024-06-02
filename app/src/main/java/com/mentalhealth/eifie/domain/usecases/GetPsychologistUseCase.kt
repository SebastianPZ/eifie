package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPsychologistUseCase @Inject constructor(
    private val psychologistRepository: PsychologistRepository
) {
    fun invoke(psychologistId: Long) = flow{
        emit(psychologistRepository.getPsychologistById(psychologistId))
    }
}