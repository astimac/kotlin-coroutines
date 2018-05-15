package com.astimac.coroutines.utils

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
open class CoroutineContextProvider @Inject constructor() {
    open val MAIN: CoroutineContext by lazy { UI }
    open val BG: CoroutineContext by lazy { CommonPool }
}