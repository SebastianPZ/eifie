package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.api.models.request.PatientRequest
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterPatientUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun invoke(request: PatientRequest) = flow {
        val result = repository.registerPatient(request)
        //TODO: Treat result data with use case requirements
        emit(result)
    }

}