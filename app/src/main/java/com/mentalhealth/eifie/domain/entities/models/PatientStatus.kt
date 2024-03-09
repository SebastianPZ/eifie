package com.mentalhealth.eifie.domain.entities.models

enum class PatientStatus(val value: String) {
    NONE(value = "Sin estado"),  //Psychologist doesn't have status
    IN_PROCESS(value = "Sin estado"),
    MINIMUM_DEPRESSION(value = "Sin estado"),
    MILD_DEPRESSION(value = "Sin estado"),
    MODERATE_DEPRESSION(value = "Sin estado"),
    HIGH_DEPRESSION(value = "Sin estado")
}