package com.mentalhealth.eifie.ui.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.domain.usecases.GetPsychologistUseCase
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
@HiltViewModel(assistedFactory = PsychologistDetailViewModel.PsychologistDetailViewModelFactory::class)
class PsychologistDetailViewModel @AssistedInject constructor(
    @Assisted private val psychologistId: Long,
    private val getPsychologistUseCase: GetPsychologistUseCase
): ViewModel() {

    private val _psychologistInfo: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )

    val psychologistInfo = _psychologistInfo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = listOf()
    )

    private val _psychologist: MutableStateFlow<Psychologist> = MutableStateFlow(Psychologist())

    val psychologist = _psychologist.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = Psychologist()
    )

    init {
        getPsychologistDetail()
    }

    private fun getPsychologistDetail() = viewModelScope.launch {
        getPsychologistUseCase.invoke(psychologistId)
            .onStart {  }
            .onEach {
                when(it) {
                    is EResult.Error -> Unit
                    is EResult.Success -> {
                        setPsychologistInfo(it.data)
                        _psychologist.value = it.data
                    }
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun setPsychologistInfo(psychologist: Psychologist) {
        _psychologistInfo.value = listOf(
            "Fecha de nacimiento" to psychologist.birthDate,
            "Edad" to "${calculateAge(psychologist.birthDate)} años",
            "Coreo" to psychologist.email,
            "Centro médico" to psychologist.hospital,
        )
    }

    @AssistedFactory
    fun interface PsychologistDetailViewModelFactory {
        fun create(psychologistId: Long): PsychologistDetailViewModel
    }
}