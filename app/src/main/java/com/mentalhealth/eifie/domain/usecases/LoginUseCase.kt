package com.mentalhealth.eifie.domain.usecases

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository) {
    fun invoke(email: String, password: String) = flow {
        emit(repository.authenticateUser(email, password))
    }

}