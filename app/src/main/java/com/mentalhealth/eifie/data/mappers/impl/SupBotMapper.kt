package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.Supporter

object SupporterMapper: Mapper<LocalSupBot, Supporter> {
    override fun mapFromEntity(entity: Supporter): LocalSupBot {
        return LocalSupBot(
            id = entity.id,
            name = entity.name,
            userId = entity.user,
            config = entity.config,
            photo = entity.photo
        )
    }

    override fun mapToEntity(model: LocalSupBot): Supporter {
        return Supporter(
            id = model.id ?: 0,
            user = model.userId ?: 0,
            name = model.name,
            config = model.config,
            photo = model.photo
        )
    }
}