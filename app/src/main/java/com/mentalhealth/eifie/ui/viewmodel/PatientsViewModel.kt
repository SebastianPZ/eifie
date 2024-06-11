package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.usecases.GetPatientsUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
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
): LazyViewModel() {

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
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> {
                        _patients.value = listOf()
                        viewState.value = ViewState.Error(it.error.message ?: "")
                    }
                    is EResult.Success -> {
                        _patients.value = it.data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch {
                _patients.value = listOf()
                viewState.value = ViewState.Error(it.message ?: "")
            }
            .launchIn(viewModelScope)
    }

}