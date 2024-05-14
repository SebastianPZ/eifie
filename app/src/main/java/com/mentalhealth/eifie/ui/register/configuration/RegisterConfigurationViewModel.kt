package com.mentalhealth.eifie.ui.register.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.util.PSYCHOLOGIST_ASSIGN
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = RegisterConfigurationViewModel.RegisterConfigurationViewModelFactory::class)
class RegisterConfigurationViewModel @AssistedInject constructor(
    @Assisted private val role: Int
): ViewModel() {

    private val _options: MutableStateFlow<List<ProfileItem>> = MutableStateFlow(listOf())
    val options = _options.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initOptions()
    }

    private fun initOptions() {
        _options.value = when(role) {
            Role.PATIENT.ordinal -> listOf(
                ProfileItem(R.drawable.ic_profile_doctor_code, PSYCHOLOGIST_ASSIGN, Router.REGISTER_PSYCHOLOGIST.route)
            )
            else -> listOf()
        }
    }

    @AssistedFactory
    fun interface RegisterConfigurationViewModelFactory {
        fun create(role: Int): RegisterConfigurationViewModel
    }
}