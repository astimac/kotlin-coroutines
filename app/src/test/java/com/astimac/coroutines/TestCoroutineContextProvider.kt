package com.astimac.coroutines

import com.astimac.coroutines.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val MAIN: CoroutineContext = Unconfined
    override val BG: CoroutineContext = Unconfined
}