package com.mentalhealth.eifie.ui.login

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.data.models.request.LoginRequest
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.LoginUseCase
import com.mentalhealth.eifie.domain.usecases.SaveUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateTokenUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.util.ERR_SAVE_USER_SESSION
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.TRY_AGAIN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveUserUseCase: SaveUserInformationUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase
): LazyViewModel() {

    private val user: LoginRequest = LoginRequest()

    private val validLoginForm: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val validForm = validLoginForm.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = false
    )

    init {
        setInitialLoginViewStatus()
    }

    fun setInitialLoginViewStatus() {
        viewState.value = ViewState.Idle
    }

    fun setFormValue(text: String, field: FormField) {
        when(field) {
            FormField.EMAIL -> user.email = text
            else -> user.password = text
        }
        validLoginForm.value = user.email.isNotBlank() && user.password.isNotBlank()
    }

    fun loginUser() = viewModelScope.launch {
        loginUseCase.invoke(user.email, user.password)
            .onStart {
                viewState.value = ViewState.Loading
            }.onEach {
                when(it) {
                    is EResult.Success -> it.run {
                        saveUserSession(data).join()
                    }
                    is EResult.Error -> it.run {
                        viewState.value = ViewState.Error("${error.message} $TRY_AGAIN")
                    }
                }
            }.catch {

            }.launchIn(viewModelScope)
    }

    private fun saveUserSession(user: User) = viewModelScope.launch {
        saveUserUseCase.invoke(user).onEach {
            when(it) {
                is EResult.Success -> {
                    viewState.value = ViewState.Success
                    getFirebaseToken()
                }
                is EResult.Error -> {
                    viewState.value = ViewState.Error("$ERR_SAVE_USER_SESSION $TRY_AGAIN")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Firebase Token", "No token")
            }

            val token = task.result
            Log.d("Firebase Token", token)
            updateToken(token)
        }
    }

    private fun updateToken(token: String) = viewModelScope.launch {
        updateTokenUseCase.invoke(token)
            .onEach {
                when(it) {
                    is EResult.Error -> Unit
                    is EResult.Success -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    fun navigateToYoutubeVideo(context: Context, id: String) {

        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
        }
    }

}