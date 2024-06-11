package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSupporterUseCase @Inject constructor(
    private val repository: SupporterRepository
) {
    fun invoke(supporter: Supporter) = flow {
        when(val result = repository.update(supporter)) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(repository.getByUser())
        }
    }
}