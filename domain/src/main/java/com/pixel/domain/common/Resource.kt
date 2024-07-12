package com.pixel.domain.common

sealed class Resource<out T> {
    data class Success<T>(
        val data: T,
    ) : Resource<T>()

    data class ServerFail(
        val error: ServerError,
    ) : Resource<Nothing>()

    data class Fail(
        val exception: Throwable,
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}
