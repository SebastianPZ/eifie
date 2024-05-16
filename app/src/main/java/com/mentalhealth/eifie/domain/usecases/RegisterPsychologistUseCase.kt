package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.network.models.request.PsychologistRequest
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterPsychologistUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun invoke(request: PsychologistRequest) = flow {
        val result = repository.registerPsychologist(request)
        emit(result)
    }

}