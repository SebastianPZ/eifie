package com.mentalhealth.eifie.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.domain.usecases.SearchPatientUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.ui.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
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
class SearchPatientViewModel @Inject constructor(
    private val searchPatient: SearchPatientUseCase
): LazyViewModel() {

    private val _patients: MutableStateFlow<List<Patient>> = MutableStateFlow(listOf())

    val patients = _patients.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private var seachJob: Job? = null

    fun search(lastname: String) {
        if(lastname.length >= 3) {
            searchPatient(lastname)
        }
    }

    private fun searchPatient(lastname: String) = viewModelScope.launch {
        seachJob?.cancelAndJoin()
        seachJob = searchPatient.invoke(lastname)
            .onStart { viewState.value = ViewState.Loading }
            .onEach {
                when(it) {
                    is EResult.Error -> it.run { viewState.value = ViewState.Error(error.message ?: "") }
                    is EResult.Success -> it.run {
                        _patients.value = data
                        viewState.value = ViewState.Success
                    }
                }
            }
            .catch { viewState.value = ViewState.Error(it.message ?: "") }
            .launchIn(viewModelScope)
    }

}