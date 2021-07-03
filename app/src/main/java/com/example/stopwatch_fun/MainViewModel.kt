package com.example.stopwatch_fun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val stopwatch: Stopwatch = Stopwatch()
) : ViewModel(), Stopwatch by stopwatch {
    override val time = stopwatch.time
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0L
        )
}