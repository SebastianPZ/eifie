package com.mentalhealth.eifie.data.mappers

interface Mapper<DataModel, Entity> {
    fun mapFromEntity(entity: Entity): DataModel
    fun mapToEntity(model: DataModel): Entity
}