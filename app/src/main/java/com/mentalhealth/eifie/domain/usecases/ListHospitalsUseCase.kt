package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.repository.HospitalRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListHospitalsUseCase@Inject constructor(
    private val repository: HospitalRepository
) {

    fun invoke() = flow {
        val result = repository.listHospitals()
        emit(result)
    }

}