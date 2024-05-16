package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.network.models.request.LoginRequest
import com.mentalhealth.eifie.data.network.models.response.PatientResponse
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.request.PatientRequest
import com.mentalhealth.eifie.data.network.models.request.PsychologistRequest
import com.mentalhealth.eifie.data.network.models.response.LoginResponse
import com.mentalhealth.eifie.data.network.models.response.PsychologistResponse

interface AuthenticationRepository {
    suspend fun authenticateUser(request: LoginRequest): DataResult<LoginResponse, Exception>
    suspend fun registerPatient(request: PatientRequest): DataResult<PatientResponse, Exception>
    suspend fun registerPsychologist(request: PsychologistRequest): DataResult<PsychologistResponse, Exception>
    suspend fun generatePsychologistCode(psychologistId: Long): DataResult<String, Exception>
    suspend fun validatePsychologistCode(accessCode: String): DataResult<PsychologistResponse, Exception>
    suspend fun assignPsychologist(patientId: Long, psychologistId: Long): DataResult<PatientResponse, Exception>
}