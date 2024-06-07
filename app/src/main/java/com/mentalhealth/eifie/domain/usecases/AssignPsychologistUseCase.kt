package com.mentalhealth.eifie.domain.usecases

import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AssignPsychologistUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val userRepository: UserRepository
) {
    fun invoke(patientId: Long, psychologist: Long) = flow {
        repository.assignPsychologist(patientId, psychologist).let {
            when(it) {
                is EResult.Error -> emit(it)
                is EResult.Success -> it.run {
                    userRepository.getUser().let { userResult ->
                        when(userResult) {
                            is EResult.Error -> emit(EResult.Success(it.data.psychologistAssigned))
                            is EResult.Success -> userResult.run {
                                userRepository.updateUser(data.apply {
                                    psychologistAssigned = it.data.psychologistAssigned
                                }).run {
                                    emit(EResult.Success(it.data.psychologistAssigned))
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}