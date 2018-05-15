package com.astimac.coroutines.utils

sealed class Result<out T : Any> {
    class Loading : Result<Nothing>()
    class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val message: String) : Result<Nothing>()
}