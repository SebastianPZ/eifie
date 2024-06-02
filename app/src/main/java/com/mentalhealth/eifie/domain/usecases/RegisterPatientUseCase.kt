package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.PatientParams
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterPatientUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun invoke(request: PatientParams) = flow {
        val result = repository.registerPatient(request)
        //TODO: Treat result data with use case requirements
        emit(result)
    }

}