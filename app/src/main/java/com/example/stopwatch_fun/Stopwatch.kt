package com.example.stopwatch_fun

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

interface Stopwatch {
    val time: Flow<Long>
    fun start()
    fun pause()
    fun reset()

    companion object {
        operator fun invoke(): Stopwatch = Default()
    }

    private class Default(
        private val tick: Long = 10L,
        private val currentTime: () -> Long = { System.currentTimeMillis() }
    ) : Stopwatch {
        private var elapsedTime: Long = 0L
        private val actions = MutableStateFlow(Action.Pause)

        override val time: Flow<Long> = actions.flatMapLatest { action ->
            when (action) {
                Action.Start -> {
                    val time = flow {
                        val initial = currentTime() - elapsedTime
                        while (coroutineContext.isActive) {
                            elapsedTime = currentTime() - initial
                            emit(elapsedTime)
                            delay(tick)
                        }
                    }
                    time.conflate()
                }
                Action.Pause -> flowOf(elapsedTime)
                Action.Reset -> {
                    elapsedTime = 0L
                    flowOf(elapsedTime)
                }
            }
        }

        override fun start() {
            actions.value = Action.Start
        }

        override fun pause() {
            actions.value = Action.Pause
        }

        override fun reset() {
            actions.value = Action.Reset
            // Optionally switch back to the previous Action state here, after the above line,
            // e.g. if you want Reset to not stop the timer, just the elapsed time.
        }

        private enum class Action {
            Pause,
            Reset,
            Start
        }
    }
}
