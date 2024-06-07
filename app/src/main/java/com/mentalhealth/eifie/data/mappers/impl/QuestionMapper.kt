package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.QuestionResponse
import com.mentalhealth.eifie.domain.entities.Question
import com.mentalhealth.eifie.domain.entities.QuestionOption

object QuestionMapper: Mapper<QuestionResponse, Question> {
    override fun mapFromEntity(entity: Question): QuestionResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: QuestionResponse): Question {
        return Question(
            id = model.questionId ?: 0,
            text = model.text ?: "",
            options = getQuestionOptionsList(model.answers ?: "")
        )
    }

    private fun getQuestionOptionsList(rawAnswers: String): List<QuestionOption> {
        return if(rawAnswers.isEmpty()) {
            listOf()
        } else {
            val answers = rawAnswers.split("|")

            return answers.map { answer ->
                val option = if(answer.contains(":")) answer.split(":")
                else answer.split(".")

                QuestionOption(
                    id = option.first().toInt(),
                    text = option.last()
                )
            }
        }
    }
}