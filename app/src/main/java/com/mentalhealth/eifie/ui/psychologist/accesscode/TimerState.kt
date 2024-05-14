package com.mentalhealth.eifie.ui.psychologist.accesscode

import com.mentalhealth.eifie.util.TimeUtil
import com.mentalhealth.eifie.util.TimeUtil.formatTime

data class TimerState(
    val timeLeft: String = TimeUtil.TIME_COUNTDOWN.formatTime(),
    val progress: Float = 1.00F
)