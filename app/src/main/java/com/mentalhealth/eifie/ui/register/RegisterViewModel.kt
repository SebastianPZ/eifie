package com.mentalhealth.eifie.ui.register

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.data.api.DataResult
import com.mentalhealth.eifie.data.api.models.request.RegisterRequest
import com.mentalhealth.eifie.data.api.models.response.HospitalResponse
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.domain.entities.states.CodeState
import com.mentalhealth.eifie.domain.usecases.AssignPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.ListHospitalsUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPatientUseCase
import com.mentalhealth.eifie.domain.usecases.ValidatePsychologistCodeUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.dropdown.DropdownItem
import com.mentalhealth.eifie.util.FormField
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
    private val listHospitalsUseCase: ListHospitalsUseCase,
    private val validateCodeUseCase: ValidatePsychologistCodeUseCase,
    private val assignPsychologist: AssignPsychologistUseCase
): LazyViewModel() {

    private val steps = listOf(Step.ROLE_DATA, Step.PERSONAL_DATA, Step.USER_DATA)

    private val validPersonalForm: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val validPersonal = validPersonalForm.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = false
    )

    private val validUserForm: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val validUser = validUserForm.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = false
    )

    private val actualStep: MutableStateFlow<Step> = MutableStateFlow(steps.first())
    val step = actualStep.stateIn(
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

    private val accessCodeError: MutableStateFlow<String> = MutableStateFlow("")
    val codeError = accessCodeError.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000, 1),
        initialValue = ""
    )

    private lateinit var navigateUp: () -> Unit

    val onBackPressed get() = navigateUp

    val user: RegisterRequest = RegisterRequest()

    val stepsSize get() = (steps.size).toString()

    init {
        setViewInitialStatus()
        initRoles()
        initHospitals()
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

    private fun initHospitals() = viewModelScope.launch {
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

    fun updateStep(navController: NavHostController, newStep: Step) {
        if(newStep.ordinal < actualStep.value.ordinal) {
            when(actualStep.value) {
                Step.ROLE_DATA -> navigateUp.invoke()
                else -> {
                    actualStep.value = newStep
                    navController.navigateUp()
                }
            }
        } else {
            actualStep.value = newStep
            navController.navigate(newStep.route.route)
        }
    }

    fun updateSelectedRole(newRole: RoleOption) {
        roleSelected.value = newRole
        roleOptions.value = roleOptions.value.map {
            it.copy(selected = it.id == newRole.id)
        }
    }

    fun registerNavigateUp(call: () -> Unit) {
        navigateUp = call
    }

    private fun registerPatientUser() = viewModelScope.launch {
        registerPatientUseCase.invoke(user.toPatientRequest())
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                viewState.value = RegisterViewState.Loading
            }.onEach { result ->
                when(result) {
                    is DataResult.Success -> {
                        if(user.psychologist != null) assignPsychologist(result.data.patientId ?: 0).join()
                        else viewState.value = RegisterViewState.Success
                    }
                    is DataResult.Error -> result.run {
                        viewState.value = RegisterViewState.Error(error.message ?: "")
                    }
                    else -> Unit
                }
            }.catch {
                Log.e("RegisterViewModel", "Error", it)
            }.launchIn(viewModelScope)
    }

    private fun registerPsychologistUser() = viewModelScope.launch {
        registerPsychologistUseCase.invoke(user.toPsychologistRequest())
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                viewState.value = RegisterViewState.Loading
            }.onEach { result ->
                when(result) {
                    is DataResult.Success -> viewState.value = RegisterViewState.Success
                    is DataResult.Error -> result.run {
                        viewState.value = RegisterViewState.Error(error.message ?: "")
                    }
                    else -> Unit
                }
            }.catch {

            }.launchIn(viewModelScope)
    }


    fun validateCode(code: String, onSuccess: (psychologist: Psychologist) -> Unit) = viewModelScope.launch {
        validateCodeUseCase.invoke(code)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }
            }.onStart {
                Log.e("RegisterViewModel", "Start Access Code validation")
                viewState.value = RegisterViewState.Loading
            }.onEach {
                when(it) {
                    is CodeState.Error -> {
                        accessCodeError.value = it.error
                        viewState.value = RegisterViewState.Idle
                    }
                    is CodeState.Success -> {
                        user.psychologist = it.psychologist.id
                        onSuccess(it.psychologist)
                        viewState.value = RegisterViewState.Idle
                    }
                    else -> viewState.value = RegisterViewState.Loading
                }
            }.catch {
                Log.e("RegisterViewModel", "Error", it)
            }.launchIn(viewModelScope)
    }

    private fun assignPsychologist(patientId: Long) = viewModelScope.launch {
        assignPsychologist.invoke(patientId, user.psychologist ?: 0)
            .retry(3L) { error -> (error is IOException).also { if(it) delay(1000) }}
            .onStart { viewState.value = RegisterViewState.Loading }
            .onEach { result ->
                when(result) {
                    is DataResult.Success -> viewState.value = RegisterViewState.Success
                    is DataResult.Error -> result.run {
                        viewState.value = RegisterViewState.Success
                    }
                    else -> Unit
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun setFormValue(text: String, field: FormField) {
        when(field) {
            FormField.FIRSTNAME -> user.firstName = text
            FormField.LASTNAME -> user.lastName = text
            FormField.BIRTHDATE -> user.birthDate = text
            FormField.EMAIL -> user.email = text
            FormField.PASSWORD -> user.password = text
            else -> user.checkPassword = text
        }
        validPersonalForm.value = user.firstName.isNotBlank() && user.lastName.isNotBlank()
                && user.birthDate.isNotBlank()
        validUserForm.value = user.email.isNotBlank() && user.password.isNotBlank()
                && user.checkPassword.isNotBlank()
    }

    fun registerUser() {
        //viewState.value = RegisterViewState.Success
        when(roleSelected.value?.id) {
            Role.PATIENT.ordinal -> {
                Log.d("Register Patient", user.toString())
                registerPatientUser()
            }
            Role.PSYCHOLOGIST.ordinal -> {
                Log.d("Register Psychologist", user.toString())
                registerPsychologistUser()
            }
            else -> Unit
        }
    }

}