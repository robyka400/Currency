package com.nagyrobi.core.model

/**
 * With this example it's easy to showcase the use of a Resource wrapper,
 * that wrap's the value in a corresponding state, in order that these items can be emitted through
 * the stream.
 * However I wouldn't use resources, only if I'm sure, that after mapping to a resource the
 * data doesn't have to be processed, since doing any processing (mapping, filtering, etc...) with
 * resources can bring many boilerplate
 */
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>()
}