package com.mentalhealth.eifie.ui.mappers

interface Mapper<UIModel, Entity> {
    fun mapFromEntity(entity: Entity): UIModel
    fun mapToEntity(model: UIModel): Entity
}