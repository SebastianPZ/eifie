package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSupporterUseCase @Inject constructor(
    private val repository: SupporterRepository,
    private val userRepository: UserRepository
) {
    fun invoke(supporter: Supporter, includeName: Boolean) = flow {
        if(includeName) {
            when(val result = userRepository.getUser()) {
                is EResult.Error -> Unit
                is EResult.Success -> result.run {
                    supporter.config = "Hola soy ${data.userName}, tengo ${data.age} años y quiero que actúes como una personal normal. Eres un consejero para personas con síntomas de depresión. Eres amiga de la persona que te escribirá y tu nombre es ${supporter.name}."
                }
            }
        }

        when(val result = repository.update(supporter)) {
            is EResult.Error -> emit(result)
            is EResult.Success -> emit(repository.getByUser())
        }
    }
}