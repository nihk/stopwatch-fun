package com.example.stopwatch_fun

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.math.max

class Timer(
    private val duration: Long,
    private val stopwatch: Stopwatch = Stopwatch()
) : Stopwatch by stopwatch {
    override val time: Flow<Long> = stopwatch.time
        .map { elapsed -> max(0L, duration - elapsed) }
        .onEach { remaining ->
            if (remaining <= 0L) {
                stopwatch.pause()
            }
        }
}