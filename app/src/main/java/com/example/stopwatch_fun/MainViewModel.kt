package com.example.stopwatch_fun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val timer: Timer = Timer(2_000)
) : ViewModel(), Stopwatch by timer {
    override val time = timer.time.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = 0L
    )
}
