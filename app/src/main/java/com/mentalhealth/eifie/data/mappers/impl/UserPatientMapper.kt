package com.mentalhealth.eifie.data.mappers.impl

import com.mentalhealth.eifie.data.mappers.Mapper
import com.mentalhealth.eifie.data.models.response.UserPatientResponse
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.util.getUserName

object UserPatientMapper: Mapper<UserPatientResponse, Patient> {
    override fun mapFromEntity(entity: Patient): UserPatientResponse {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(model: UserPatientResponse): Patient {
        return Patient(
            id = model.patientId ?: 0,
            psychologistAssigned = model.psychologistAssignedId ?: 0,
            firstname = model.user?.firstName ?: "",
            lastname = model.user?.lastName ?: "",
            username = model.user?.let { getUserName(it.firstName ?: "", it.lastName ?: "") } ?: "",
            birthDate = model.user?.birthDate ?: "",
            email = model.user?.email ?: "",
            picture = model.user?.picture?.url,
            state = "Ok"
        )
    }
}