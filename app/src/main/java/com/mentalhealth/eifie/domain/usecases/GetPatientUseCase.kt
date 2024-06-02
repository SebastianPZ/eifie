package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.PatientRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPatientUseCase @Inject constructor(
    private val patientRepository: PatientRepository
) {
    fun invoke(patientId: Long) = flow{
        emit(patientRepository.getPatientById(patientId))
    }
}