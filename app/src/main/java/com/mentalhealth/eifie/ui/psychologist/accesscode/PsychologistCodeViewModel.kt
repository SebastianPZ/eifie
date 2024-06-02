package com.mentalhealth.eifie.ui.psychologist.accesscode

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.mentalhealth.eifie.domain.entities.EResult
import com.mentalhealth.eifie.domain.usecases.GeneratePsychologistCodeUseCase
import com.mentalhealth.eifie.ui.common.LazyViewModel
import com.mentalhealth.eifie.util.TimeUtil
import com.mentalhealth.eifie.util.TimeUtil.formatTime
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

@HiltViewModel(assistedFactory = PsychologistCodeViewModel.PsychologistCodeViewModelFactory::class)
class PsychologistCodeViewModel @AssistedInject constructor(
    @Assisted private val psychologist: Long,
    private val generatePsychologistCode: GeneratePsychologistCodeUseCase
): LazyViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private val _accessCode: MutableStateFlow<String> = MutableStateFlow("")

    val accessCode = _accessCode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = ""
    )

    private val _time: MutableStateFlow<TimerState> = MutableStateFlow(TimerState())

    val time = _time.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000, 1),
        initialValue = TimerState()
    )

    init {
        generateAccessCode()
    }

    private fun generateAccessCode() =viewModelScope.launch {
        generatePsychologistCode.invoke(psychologist)
            .onStart {  }
            .onEach { result ->
                when(result) {
                    is EResult.Success -> result.run {
                        _accessCode.value = data
                        startTimer()
                    }
                    else -> Unit
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(TimeUtil.TIME_COUNTDOWN, 1000) {
            override fun onTick(millisRemaining: Long) {
                val progressValue = millisRemaining.toFloat() / TimeUtil.TIME_COUNTDOWN
                handleTimerValues(millisRemaining.formatTime(), progressValue)
            }

            override fun onFinish() {
                _time.value = TimerState()
                generateAccessCode()
            }
        }.start()
    }

    private fun handleTimerValues(text: String, progress: Float) {
        _time.value = TimerState(timeLeft = text, progress = progress)
    }

    @AssistedFactory
    fun interface PsychologistCodeViewModelFactory {
        fun create(psychologist: Long): PsychologistCodeViewModel
    }
}