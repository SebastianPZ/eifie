package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.local.database.entities.LocalSupBot
import com.mentalhealth.eifie.data.mappers.Mapper

object SupporterFirebaseMapper: Mapper<LocalSupBot, MutableMap<String, Any?>> {
    override fun mapFromEntity(entity: MutableMap<String, Any?>): LocalSupBot {
        return LocalSupBot(
            id = entity["id"] as Long?,
            userId = entity["userId"] as Long?,
            name = entity["name"] as String,
            config = entity["config"] as String,
            photo = entity["photo"] as String?
        )
    }

    override fun mapToEntity(model: LocalSupBot): MutableMap<String, Any?> {
        return hashMapOf(
            "id" to model.id,
            "userId" to model.userId,
            "name" to model.name,
            "config" to model.config,
            "photo" to model.photo
        )
    }
}