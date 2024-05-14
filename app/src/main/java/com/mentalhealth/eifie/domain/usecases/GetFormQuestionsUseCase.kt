package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.data.mock.FormFakeData
import com.mentalhealth.eifie.domain.entities.models.Question
import com.mentalhealth.eifie.domain.entities.models.QuestionOption
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFormQuestionsUseCase @Inject constructor() {
    fun invoke(formId: Int) = flow {
        val questions = FormFakeData.getQuestionsByForm().map {
            Question(
                id = it.questionId ?: 0,
                text = it.text ?: "",
                options = getQuestionOptionsList(it.answers ?: "")
            )
        }
        emit(questions)
    }

    private fun getQuestionOptionsList(rawAnswers: String): List<QuestionOption> {
        return if(rawAnswers.isEmpty()) {
            listOf()
        } else {
            val answers = rawAnswers.split("|")

            return answers.map { answer ->
                val option = answer.split(":")

                QuestionOption(
                    id = option.first().toInt(),
                    text = option.last()
                )
            }
        }
    }

}