package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPatientsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val psychologistRepository: PsychologistRepository
) {
    fun invoke() = flow {
        when(val result = userRepository.getUser()) {
            is EResult.Error -> emit(result)
            is EResult.Success -> result.run {
                emit(psychologistRepository.listPatients(data.uid))
            }
        }
    }
}