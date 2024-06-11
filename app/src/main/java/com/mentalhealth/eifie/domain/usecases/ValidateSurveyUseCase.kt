package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.SurveyRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val userRepository: UserRepository
) {
    fun invoke() = flow {
        when(val result = userRepository.getUser()) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(surveyRepository.validateSurvey(result.data.uid))
        }
    }
}