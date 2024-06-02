package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.entities.Psychologist

interface PsychologistRepository {
    suspend fun getPsychologistById(psychologistId: Long): EResult<Psychologist, Exception>
    suspend fun listPatients(psychologistId: Long): EResult<List<Patient>, Exception>
}