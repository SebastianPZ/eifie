package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Form
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.domain.usecases.GetFormListUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.util.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getFormListUseCase: GetFormListUseCase,
): ViewModel() {

    private val _userRole: MutableStateFlow<Role> = MutableStateFlow(Role.PATIENT)

    val userRole = _userRole.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Role.PATIENT
    )

    private val _userName: MutableStateFlow<String> = MutableStateFlow(emptyString())

    val userName = _userName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = emptyString()
    )

    private val _formList: MutableStateFlow<List<Form>> = MutableStateFlow(listOf())

    val formList = _formList.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initUserCustomHome()
    }

    private fun initUserCustomHome() = viewModelScope.launch {
        getUserInformationUseCase.invoke()
            .onStart {

            }.onEach {
                when(it) {
                    is EResult.Success -> it.run { configUserHome(data).join() }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun configUserHome(user: User) = viewModelScope.launch {
        _userRole.value = user.role
        _userName.value = user.firstName

        when(user.role) {
            Role.PATIENT -> getFormListUseCase().join()
            Role.PSYCHOLOGIST -> Unit
        }
    }

    private fun getFormListUseCase() = viewModelScope.launch {
        getFormListUseCase.invoke()
            .onStart {

            }.onEach {
                _formList.value = it
            }.launchIn(viewModelScope)
    }

}