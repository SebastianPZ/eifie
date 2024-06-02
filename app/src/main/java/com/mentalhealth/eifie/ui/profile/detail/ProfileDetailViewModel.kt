package com.mentalhealth.eifie.ui.profile.detail

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.LogoutUserUseCase
import com.mentalhealth.eifie.ui.MainActivity
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.ui.profile.UserPhoto
import com.mentalhealth.eifie.util.BIRTHDATE_TITLE
import com.mentalhealth.eifie.util.EMAIL_TITLE
import com.mentalhealth.eifie.util.FIRSTNAME_TITLE
import com.mentalhealth.eifie.util.LASTNAME_TITLE
import com.mentalhealth.eifie.util.PSYCHOLOGIST_ASSIGN
import com.mentalhealth.eifie.util.PSYCHOLOGIST_CODE
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
class ProfileDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserInformationUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : LazyViewModel() {

    private val _userPhoto: MutableStateFlow<UserPhoto?> = MutableStateFlow(null)

    val userPhoto = _userPhoto.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = null
    )

    lateinit var user: User

    /*init {
        initUserInformation()
    }*/

    fun initUserInformation() = viewModelScope.launch {
        getUserUseCase.invoke()
            .onStart {
                viewState.value = ProfileDetailViewState.Loading
            }.onEach {
                when(it) {
                    is EResult.Success -> it.run {
                        viewState.value = ProfileDetailViewState.Success(handleUserData(data))
                    }
                    is EResult.Error -> it.run {
                        viewState.value = ProfileDetailViewState.Error
                    }
                }
            }.catch {

            }.launchIn(viewModelScope)
    }

    private fun handleUserData(user: User): ProfileDetailData {
        this.user = user
        handleUserPhoto(user) //init photo values
        return ProfileDetailData(
            data = listOf(
                ProfileItem(R.drawable.ic_profile_user, FIRSTNAME_TITLE, user.firstName),
                ProfileItem(R.drawable.ic_profile_user_2, LASTNAME_TITLE, user.lastName),
                ProfileItem(R.drawable.ic_profile_birthday, BIRTHDATE_TITLE, user.birthDate),
                ProfileItem(R.drawable.ic_profile_email, EMAIL_TITLE, user.email),
            ),
            options = handleUserOptions(user)
        )
    }

    private fun handleUserPhoto(user: User) {
        _userPhoto.value = UserPhoto(
            username = user.userName,
            photoUri = if(user.picture.isNullOrEmpty()) null else user.picture,
        )
    }

    private fun handleUserOptions(user: User): List<ProfileItem> {
        return when(user.role) {
            Role.PATIENT -> listOf(
                ProfileItem(R.drawable.ic_profile_doctor_code, PSYCHOLOGIST_ASSIGN,
                    "${Router.PSYCHOLOGIST_DETAIL.route}${user.psychologistAssigned}")
            )
            Role.PSYCHOLOGIST -> listOf(
                ProfileItem(R.drawable.ic_profile_doctor_code, PSYCHOLOGIST_CODE, Router.PSYCHOLOGIST_CODE.route)
            )
        }
    }

    fun logoutUser(context: ComponentActivity) = viewModelScope.launch {
        logoutUserUseCase.invoke()
            .onEach {
                when(it) {
                    is EResult.Success -> {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    }
                    is EResult.Error -> {
                        Log.e("Logout", "Error", it.error)
                    }
                }
            }.launchIn(viewModelScope)
    }
}