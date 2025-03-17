package com.example.droidchat.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeCallResult(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T,
) = withContext(dispatcher) {
    runCatching {
        block()
    }
}