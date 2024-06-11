package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.request.AnswerScore
import com.mentalhealth.eifie.domain.entities.Answer

object AnswerMapper: Mapper<AnswerScore, Answer> {
    override fun mapFromEntity(entity: Answer): AnswerScore {
        return AnswerScore(
            questionId = entity.questionId,
            score = entity.score
        )
    }

    override fun mapToEntity(model: AnswerScore): Answer {
        TODO("Not yet implemented")
    }
}