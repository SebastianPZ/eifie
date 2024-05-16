package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.network.DataResult
import com.mentalhealth.eifie.data.network.performApiCall
import com.mentalhealth.eifie.data.network.models.response.HospitalResponse
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HospitalDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): HospitalRepository {

    override suspend fun listHospitals(): DataResult<List<HospitalResponse>, Exception> = withContext(dispatcher) {
        performApiCall(
            { api.getHospitals() },
            { response -> response?.data }
        )
    }

}