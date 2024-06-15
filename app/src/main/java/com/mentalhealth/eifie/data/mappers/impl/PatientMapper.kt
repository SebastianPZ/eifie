package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.PatientResponse
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.util.getUserName

object PatientMapper: Mapper<PatientResponse, Patient> {
    override fun mapFromEntity(entity: Patient): PatientResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: PatientResponse): Patient {
        return Patient(
            id = model.patientId ?: 0,
            psychologistAssigned = model.psychologistAssignedId ?: 0,
            firstname = model.firstName ?: "",
            lastname = model.lastName ?: "",
            username = getUserName(model.firstName ?: "", model.lastName ?: "")
        )
    }
}