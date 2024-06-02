package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.usecases.GetPatientsUseCase
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
class PatientsViewModel @Inject constructor(
    private val getPatientsUseCase: GetPatientsUseCase
): ViewModel() {

    private val _patients: MutableStateFlow<List<Patient>> = MutableStateFlow(listOf())

    val patients = _patients.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    init {
        listPatients()
    }

    fun refreshPatients() {
        listPatients()
    }

    private fun listPatients() = viewModelScope.launch {
        getPatientsUseCase.invoke()
            .onStart {  }
            .onEach {
                when(it) {
                    is EResult.Error -> _patients.value = listOf()
                    is EResult.Success -> it.run {
                        _patients.value = data
                    }
                }
            }
            .catch { _patients.value = listOf() }
            .launchIn(viewModelScope)
    }

}