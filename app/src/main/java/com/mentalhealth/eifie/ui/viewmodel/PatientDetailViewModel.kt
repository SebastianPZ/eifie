package com.mentalhealth.eifie.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.usecases.GetPatientUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.models.PatientInfoTile
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.ui.theme.CustomRed
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightGreen
import com.mentalhealth.eifie.util.calculateAge
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PatientDetailViewModel.PatientDetailViewModelFactory::class)
class PatientDetailViewModel @AssistedInject constructor(
    @Assisted private val patientId: Long,
    private val getPatient: GetPatientUseCase
): LazyViewModel() {

    val options = mutableStateListOf<ProfileItem>()

    private val _ageInformation: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )

    val ageInformation = _ageInformation.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _generalInformation: MutableStateFlow<List<PatientInfoTile>> = MutableStateFlow(
        listOf()
    )

    val generalInformation = _generalInformation.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _patient: MutableStateFlow<Patient> = MutableStateFlow(Patient())

    val patient = _patient.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Patient()
    )

    init {
        initPatientItems()
        initPatientInfo()
    }

    private fun initPatientItems() {
        /*ProfileItem(
            icon = R.drawable.ic_profile_user,
            label = "Informe General",
            value = ""
        )*/
        options.addAll(
            listOf(
                ProfileItem(
                    icon = R.drawable.ic_profile_user,
                    label = "Historial Mensajes",
                    value = "${Router.PATIENT_DETAIL.route}$patientId/chats"
                ),
                ProfileItem(
                    icon = R.drawable.ic_profile_user,
                    label = "Historial Citas",
                    value = "${Router.PATIENT_DETAIL.route}$patientId/appointments"
                )
            )
        )
    }

    private fun initPatientInfo() = viewModelScope.launch {
        getPatient.invoke(patientId)
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> it.run{
                        ViewState.Error(error.message ?: "")
                    }
                    is EResult.Success -> {
                        setPatientInfo(it.data)
                        _patient.value = it.data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    private fun setPatientInfo(patient: Patient) {
        _ageInformation.value = listOf(
            "Fecha de nacimiento" to patient.birthDate,
            "Edad" to "${calculateAge(patient.birthDate)} años"
        )

        _generalInformation.value = listOf(
            PatientInfoTile("Correo", patient.email),
            PatientInfoTile(
                "Estado", patient.status(), when (patient.status) {
                    1 -> DarkGreen
                    2 -> CustomRed
                    else -> DarkGray
                }
            ),
            PatientInfoTile("Última evaluación de estado", patient.lastStatusUpdateDate)
        )
    }

    @AssistedFactory
    fun interface PatientDetailViewModelFactory {
        fun create(patientId: Long): PatientDetailViewModel
    }
}