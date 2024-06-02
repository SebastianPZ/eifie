package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.SupBot
import com.mentalhealth.eifie.domain.repository.SupBotRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateSupportBotUseCase @Inject constructor(
    private val repository: SupBotRepository
) {
    fun invoke(config: String) = flow {
        emit(repository.saveSupBot(SupBot(
            name = "Inami",
            config = ""
        )))
    }
}