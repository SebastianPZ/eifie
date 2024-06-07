package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.PatientRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPatientUseCase @Inject constructor(
    private val patientRepository: PatientRepository
) {
    fun invoke(lastname: String) = flow {
        emit(patientRepository.searchPatientBy(lastname))
    }
}