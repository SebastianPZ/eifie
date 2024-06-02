package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.PatientParams
import com.mentalhealth.eifie.domain.entities.PsychologistParams
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.domain.entities.User

interface AuthenticationRepository {
    suspend fun authenticateUser(email: String, password: String): EResult<User, Exception>
    suspend fun registerPatient(request: PatientParams): EResult<Patient, Exception>
    suspend fun registerPsychologist(request: PsychologistParams): EResult<Psychologist, Exception>
    suspend fun generatePsychologistCode(psychologistId: Long): EResult<String, Exception>
    suspend fun validatePsychologistCode(accessCode: String): EResult<Psychologist, Exception>
    suspend fun assignPsychologist(patientId: Long, psychologistId: Long): EResult<Patient, Exception>
}