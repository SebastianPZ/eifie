package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.models.response.HospitalResponse

fun interface HospitalRepository {
    suspend fun listHospitals(): EResult<List<HospitalResponse>, Exception>
}