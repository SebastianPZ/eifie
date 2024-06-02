package com.mentalhealth.eifie.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.usecases.GetPatientUseCase
import com.mentalhealth.eifie.ui.profile.ProfileItem
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailViewModel
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
import javax.inject.Inject

@HiltViewModel(assistedFactory = PatientDetailViewModel.PatientDetailViewModelFactory::class)
class PatientDetailViewModel @AssistedInject constructor(
    @Assisted private val patientId: Long,
    private val getPatient: GetPatientUseCase
): ViewModel() {

    val options = mutableStateListOf<ProfileItem>()

    private val _patientInfo: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )

    val patientInfo = _patientInfo.stateIn(
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
        options.addAll(
            listOf(
                ProfileItem(
                    icon = R.drawable.ic_profile_user,
                    label = "Informe General",
                    value = ""
                ),
                ProfileItem(
                    icon = R.drawable.ic_profile_user,
                    label = "Historial Mensajes",
                    value = ""
                ),
                ProfileItem(
                    icon = R.drawable.ic_profile_user,
                    label = "Historial Citas",
                    value = ""
                )
            )
        )
    }

    private fun initPatientInfo() = viewModelScope.launch {
        getPatient.invoke(patientId)
            .onStart {  }
            .onEach {
                when(it) {
                    is EResult.Error -> Unit
                    is EResult.Success -> {
                        setPsychologistInfo(it.data)
                        _patient.value = it.data
                    }
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun setPsychologistInfo(psychologist: Patient) {
        _patientInfo.value = listOf(
            "Fecha de nacimiento" to psychologist.birthDate,
            "Edad" to "${calculateAge(psychologist.birthDate)} años",
            "Correo" to psychologist.email,
            "Estado" to psychologist.state,
        )
    }

    @AssistedFactory
    fun interface PatientDetailViewModelFactory {
        fun create(patientId: Long): PatientDetailViewModel
    }
}