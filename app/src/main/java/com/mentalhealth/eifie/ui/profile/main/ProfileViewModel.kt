package com.mentalhealth.eifie.ui.profile.main

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.ui.profile.UserPhoto
import com.mentalhealth.eifie.util.AGE_TITLE
import com.mentalhealth.eifie.util.HOSPITAL_TITLE
import com.mentalhealth.eifie.util.STATUS_TITLE
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
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserInformationUseCase
) : LazyViewModel() {

    private lateinit var user: User

    private val _profileItems: MutableStateFlow<List<ProfileItem>> = MutableStateFlow(emptyList())

    val profileItems = _profileItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = emptyList()
    )

    private val _userPhoto: MutableStateFlow<UserPhoto?> = MutableStateFlow(null)

    val userPhoto = _userPhoto.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = null
    )

    fun initUserInformation() = viewModelScope.launch {
        getUserUseCase.invoke()
            .onStart {
                viewState.value = ViewState.Loading
            }.onEach {
                when(it) {
                    is EResult.Success -> it.run {
                        _profileItems.value = handleProfileData(data)
                        viewState.value = ViewState.Success
                    }
                    is EResult.Error -> it.run {
                        viewState.value = ViewState.Error(error.message ?: "")
                    }
                }
            }.catch {

            }.launchIn(viewModelScope)
    }

    private fun handleUserPhoto(user: User) {
        _userPhoto.value = UserPhoto(
            username = user.userName,
            photoUri = if(user.picture.isNullOrEmpty()) null else user.picture,
        )
    }

    private fun handleProfileData(user: User): List<ProfileItem> {
        this.user = user
        handleUserPhoto(user)
        return listOf(
            ProfileItem(R.drawable.ic_age, AGE_TITLE, user.age.toString()),
            ProfileItem(R.drawable.ic_status, STATUS_TITLE, user.status.value),
            ProfileItem(R.drawable.ic_hospital, HOSPITAL_TITLE, user.hospitalName)
        )
    }
}