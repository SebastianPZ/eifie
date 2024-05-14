package com.mentalhealth.eifie.ui.register.main

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.request.RegisterRequest
import com.mentalhealth.eifie.data.api.models.response.HospitalResponse
import com.mentalhealth.eifie.data.api.models.response.PatientResponse
import com.mentalhealth.eifie.domain.entities.models.PersonalData
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.models.UserData
import com.mentalhealth.eifie.domain.usecases.ListHospitalsUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPatientUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.dropdown.DropdownItem
import com.mentalhealth.eifie.ui.register.RegisterViewState
import com.mentalhealth.eifie.ui.register.role.RoleOption
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.register.psychologist.RegisterPsychologistViewState
import com.mentalhealth.eifie.util.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val registerPsychologistUseCase: RegisterPsychologistUseCase,
    private val listHospitalsUseCase: ListHospitalsUseCase
): LazyViewModel() {

    private val steps = listOf(
        Step(order = Step.FIRST, title = "Rol"),
        Step(order = Step.SECOND, title = "Datos"),
        Step(order = Step.THIRD, title = "Usuario")
    )

    private val _personalData: MutableStateFlow<PersonalData> = MutableStateFlow(PersonalData())

    val personalData = _personalData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = PersonalData()
    )

    private val _userData: MutableStateFlow<UserData> = MutableStateFlow(UserData())

    val userData = _userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = UserData()
    )

    private val _actualStep: MutableStateFlow<Step> = MutableStateFlow(steps.first())
    val actualStep = _actualStep.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = steps.first()
    )

    private val roleOptions: MutableStateFlow<List<RoleOption>> = MutableStateFlow(emptyList())
    val roles = roleOptions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = emptyList()
    )

    private val roleSelected: MutableStateFlow<RoleOption?> = MutableStateFlow(roleOptions.value.firstOrNull())
    val role = roleSelected.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = roleOptions.value.firstOrNull()
    )

    private val hospitalsOptions: MutableStateFlow<List<DropdownItem>> = MutableStateFlow(emptyList())
    val hospitals = hospitalsOptions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = emptyList()
    )

    val user by lazy { initRegisterUser() }

    val stepsSize get() = steps.size

    init {
        setViewInitialStatus()
        initRoles()
    }
    private fun initRegisterUser(): RegisterRequest {
        return RegisterRequest(
            firstName = personalData.value.firstname,
            lastName = personalData.value.lastname,
            birthDate = personalData.value.birthdate,
            email = userData.value.email,
            password = userData.value.password,
            hospital = personalData.value.hospital
        )
    }

    fun setViewInitialStatus() {
        viewState.value = RegisterViewState.Idle
    }

    private fun initRoles() {
        roleOptions.value = listOf(
            RoleOption(
                Role.PATIENT.ordinal, Role.PATIENT.text,
                Role.PATIENT.abb, R.drawable.ic_patient, false),
            RoleOption(
                Role.PSYCHOLOGIST.ordinal, Role.PSYCHOLOGIST.text,
                Role.PSYCHOLOGIST.abb, R.drawable.ic_psycologyst, false)
        )

        updateSelectedRole(roleOptions.value.first())
    }

    fun initHospitals() = viewModelScope.launch {
        listHospitalsUseCase.invoke()
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {

            }.onEach { result ->
                when(result) {
                    is DataResult.Success -> handleDropdownHospitals(result.data)
                    else -> handleDropdownHospitals()
                }
            }.catch {
                handleDropdownHospitals()
            }.launchIn(viewModelScope)
    }

    private fun handleDropdownHospitals(list: List<HospitalResponse> = emptyList()) {
        hospitalsOptions.value = list.map {
            DropdownItem(it.hospitalId ?: 0, it.name ?: emptyString())
        }
    }

    fun updateStep(step: Int) {
        _actualStep.value = steps.first { it.order == step }
    }

    fun updateSelectedRole(newRole: RoleOption) {
        roleSelected.value = newRole
        roleOptions.value = roleOptions.value.map {
            it.copy(selected = it.id == newRole.id)
        }
    }

    private fun registerPatientUser() = viewModelScope.launch {
        registerPatientUseCase.invoke(user.toPatientRequest())
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }}
            .onStart { viewState.value = RegisterViewState.Loading }
            .onEach { result ->
                when(result) {
                    is DataResult.Success -> result.data.run {
                        viewState.value = RegisterViewState.Success(user = patientId ?: 0)
                    }
                    is DataResult.Error -> result.run {
                        viewState.value = RegisterViewState.Error(error.message ?: "")
                    }
                    else -> Unit
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun handleDataResultAfterAssign(result: DataResult<PatientResponse, Exception>) {
        when(result) {
            is DataResult.Success -> viewState.value = RegisterPsychologistViewState.Success
            is DataResult.Error -> result.run {
                viewState.value = RegisterPsychologistViewState.Success
            }
            else -> Unit
        }
    }

    private fun registerPsychologistUser() = viewModelScope.launch {
        registerPsychologistUseCase.invoke(user.toPsychologistRequest())
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                viewState.value = RegisterViewState.Loading
            }.onEach { result ->
                when(result) {
                    is DataResult.Success -> result.data.run {
                        viewState.value = RegisterViewState.Success(user = psychologistId ?: 0)
                    }
                    is DataResult.Error -> result.run {
                        viewState.value = RegisterViewState.Error(error.message ?: "")
                    }
                    else -> Unit
                }
            }.catch {

            }.launchIn(viewModelScope)
    }


    fun setPersonalData(personalData: PersonalData) {
        _personalData.value = personalData
    }

    fun setUserData(userData: UserData) {
        _userData.value = userData
    }

    fun registerUser() {
        when(roleSelected.value?.id) {
            Role.PATIENT.ordinal -> {
                registerPatientUser()
            }
            Role.PSYCHOLOGIST.ordinal -> {
                registerPsychologistUser()
            }
            else -> Unit
        }
    }

}