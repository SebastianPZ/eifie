package com.mentalhealth.eifie.ui.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.LogoutUserUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserPhotoUseCase
import com.mentalhealth.eifie.ui.MainActivity
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.util.AGE_TITLE
import com.mentalhealth.eifie.util.BIRTHDATE_TITLE
import com.mentalhealth.eifie.util.EMAIL_TITLE
import com.mentalhealth.eifie.util.FIRSTNAME_TITLE
import com.mentalhealth.eifie.util.HOSPITAL_TITLE
import com.mentalhealth.eifie.util.LASTNAME_TITLE
import com.mentalhealth.eifie.util.NO_INFO
import com.mentalhealth.eifie.util.STATUS_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserInformationUseCase,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
    private val updateUserUseCase: UpdateUserInformationUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : LazyViewModel() {

    private lateinit var userSession: UserSession
    private lateinit var postUpdate: () -> Unit

    init {
        getUserInformation()
    }

    fun registerPostUpdate(call: () -> Unit) {
        postUpdate = call
    }

    private fun getUserInformation() = viewModelScope.launch {
        getUserUseCase.invoke()
            .onStart {
                viewState.value = ProfileViewState.Loading
            }.onEach {
                when(it) {
                    DataResult.Loading -> viewState.value = ProfileViewState.Loading
                    is DataResult.Success -> it.run {
                        viewState.value = ProfileViewState.Success(handleProfileValues(data))
                    }
                    is DataResult.Error -> it.run {
                        viewState.value = ProfileViewState.Error
                    }
                }
            }.catch {

            }.launchIn(viewModelScope)
    }

    private fun handleProfileValues(user: UserSession): ProfileValues {
        userSession = user
        return ProfileValues(
            username = user.userName.ifEmpty { NO_INFO },
            uri = if(user.picture.isNullOrEmpty()) null else user.picture,
            detailItems = listOf(
                ProfileItem(R.drawable.ic_status, FIRSTNAME_TITLE, user.firstName),
                ProfileItem(R.drawable.ic_hospital, LASTNAME_TITLE, user.lastName),
                ProfileItem(R.drawable.ic_hospital, BIRTHDATE_TITLE, user.birthDate),
                ProfileItem(R.drawable.ic_hospital, EMAIL_TITLE, user.email),
            )
        )
    }

    fun logoutUser(context: ComponentActivity) = viewModelScope.launch {
        logoutUserUseCase.invoke()
            .onEach {
                when(it) {
                    DataResult.Loading -> Unit
                    is DataResult.Success -> {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    }
                    is DataResult.Error -> {
                        Log.e("Logout", "Error", it.error)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun cancelPhotoUpdate() {
        viewState.value = ProfileViewState.Success(handleProfileValues(userSession))
    }

    fun updatePhotoResource(uri: Uri) {
        viewState.value = ProfileViewState.EditPhoto(uri)
    }

    fun saveUserPhoto(uri: Uri?) = viewModelScope.launch {
        userSession.picture = uri.toString()
        updateUserPhoto().await().let {
            when(it) {
                is DataResult.Success -> it.run {
                    handleInformationUpdate(it.data).await()
                }
                is DataResult.Error -> Unit
                else -> Unit
            }
        }
        viewState.value = ProfileViewState.Success(handleProfileValues(userSession))
    }

    private fun updateUserPhoto() = viewModelScope.async {
        updateUserPhotoUseCase.invoke(userSession).singleOrNull()
    }

    private fun updateUserInformation(user: UserSession) = viewModelScope.async {
        updateUserUseCase.invoke(user).singleOrNull()
    }

    private fun handleInformationUpdate(user: UserSession) = viewModelScope.async {
        updateUserInformation(user).await().let {
            when(it) {
                is DataResult.Success -> {
                    userSession = user
                    postUpdate.invoke()
                }
                else -> Unit
            }
        }
    }
}