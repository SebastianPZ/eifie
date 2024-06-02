package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient

fun interface PatientRepository {
    suspend fun getPatientById(patientId: Long): EResult<Patient, Exception>
}