package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrieveSupporterUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository,
    private val userRepository: UserRepository
) {
    fun invoke() = flow {
        supporterRepository.getByUser().let {
            when(it) {
                is EResult.Error -> emit(getFirebaseSupporter())
                is EResult.Success -> emit(it)
            }
        }
    }

    private suspend fun getFirebaseSupporter(): EResult<Supporter, Exception> {
        return when(val result = supporterRepository.getSupporterBackUp()) {
            is EResult.Error -> saveSupporter()
            is EResult.Success -> result
        }
    }

    private suspend fun saveSupporter(): EResult<Supporter, Exception> {
        return userRepository.getUser().let { userResult ->
            when(userResult) {
                is EResult.Error -> userResult
                is EResult.Success -> userResult.run {
                    val prompt = "Hola soy ${data.userName}, tengo ${data.age} años y quiero que actúes como una personal normal. Eres un consejero para personas con síntomas de depresión. Eres amiga de la persona que te escribirá y tu nombre es Eifie."
                    createSupporter(user = data.uid, config = prompt)
                }
            }
        }
    }

    private suspend fun createSupporter(user: Long, config: String) =
        supporterRepository.save(Supporter(name = "Eifi", user = user, config = config))
}