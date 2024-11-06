package com.mentalhealth.eifie.data.repository

import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.models.response.HospitalResponse
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HospitalDefaultRepository @Inject constructor(
    private val api: ApiService,
    private val dataAccess: DataAccess,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): HospitalRepository {

    override suspend fun listHospitals(): EResult<List<HospitalResponse>, Exception> = withContext(dispatcher) {
        dataAccess.performApiCall(
            { api.getHospitals() },
            { response -> response?.data }
        )
    }

}