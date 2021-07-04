package com.example.stopwatch_fun

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.math.max

interface Timer : Stopwatch {
    val countdown: Long

    companion object {
        operator fun invoke(countdown: Long): Timer = Default(countdown)
    }

    private class Default(
        override val countdown: Long,
        private val stopwatch: Stopwatch = Stopwatch()
    ) : Timer, Stopwatch by stopwatch {
        override val time: Flow<Long> = stopwatch.time
            .map { elapsed -> max(0L, countdown - elapsed) }
            .onEach { remaining ->
                if (remaining <= 0L) {
                    stopwatch.pause()
                }
            }
    }
}