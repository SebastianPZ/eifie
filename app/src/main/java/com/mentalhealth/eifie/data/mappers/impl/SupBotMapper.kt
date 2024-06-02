package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot
import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.domain.entities.SupBot

object SupBotMapper: Mapper<LocalSupBot, SupBot> {
    override fun mapFromEntity(entity: SupBot): LocalSupBot {
        return LocalSupBot(
            name = entity.name,
            config = entity.config,
            photo = entity.photo
        )
    }

    override fun mapToEntity(model: LocalSupBot): SupBot {
        return SupBot(
            id = model.id,
            name = model.name,
            config = model.config,
            photo = model.photo
        )
    }
}