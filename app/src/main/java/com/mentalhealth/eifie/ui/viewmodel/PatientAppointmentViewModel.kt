package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.GetPatientAppointmentsUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
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

@HiltViewModel(assistedFactory = PatientAppointmentViewModel.PatientAppointmentViewModelFactory::class)
class PatientAppointmentViewModel @AssistedInject constructor(
    @Assisted private val patient: Long,
    private val getPatientAppointmentsUseCase: GetPatientAppointmentsUseCase
): LazyViewModel() {

    private val _appointments: MutableStateFlow<List<Appointment>> = MutableStateFlow(listOf())

    val appointments = _appointments.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        initAppointmentsUseCase()
    }

    private fun initAppointmentsUseCase() = viewModelScope.launch {
        getPatientAppointmentsUseCase.invoke(patient)
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> viewState.value = ViewState.Success
                    is EResult.Success -> it.run {
                        _appointments.value = data.reversed()
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    fun interface PatientAppointmentViewModelFactory {
        fun create(patient: Long): PatientAppointmentViewModel
    }
}