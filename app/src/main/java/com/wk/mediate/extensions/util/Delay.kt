package com.wk.mediate.extensions.util

import kotlinx.coroutines.*

class Delay {
    companion object {
        fun main(durationMillis: Long, block: () -> Unit): Job? = CoroutineScope(
                Dispatchers.Main).launch {
            delay(durationMillis)
            block()
        }

        fun io(durationMillis: Long, block: () -> Unit): Job? = CoroutineScope(Dispatchers.IO).launch {
            delay(durationMillis)
            block()
        }
    }
}