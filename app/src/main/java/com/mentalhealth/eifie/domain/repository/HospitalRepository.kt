package com.mentalhealth.eifie.domain.repository

import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.models.response.HospitalResponse

fun interface HospitalRepository {
    suspend fun listHospitals(): DataResult<List<HospitalResponse>, Exception>
}