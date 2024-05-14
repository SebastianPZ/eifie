package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.domain.entities.states.CodeState
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.util.ERR_ACCESS_CODE
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidatePsychologistCodeUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    fun invoke(accessCode: String) = flow {
        emit(
            when(val result = repository.validatePsychologistCode(accessCode)) {
                DataResult.Loading -> CodeState.Loading
                is DataResult.Success -> result.run {
                    CodeState.Success(Psychologist(
                        id = data.psychologistId ?: 0L,
                        firstName = data.user?.firstName ?: "",
                        lastName = data.user?.lastName ?: "",
                        picture = data.user?.picture?.url ?: ""
                    ))
                }
                is DataResult.Error -> result.run {
                    CodeState.Error(error.message ?: ERR_ACCESS_CODE)
                }
            }
        )
    }

}