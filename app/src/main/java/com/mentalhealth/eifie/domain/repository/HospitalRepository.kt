package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.response.HospitalResponse

fun interface HospitalRepository {
    suspend fun listHospitals(): DataResult<List<HospitalResponse>, Exception>
}