package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSurveyQuestionsUseCase @Inject constructor(
    private val repository: SurveyRepository
) {
    fun invoke() = flow {
        emit(repository.getQuestions())
    }
}