package com.nagyrobi.core.model

sealed class Resource<T>() {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>()
}