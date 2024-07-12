package com.pixel.domain.common

data class ServerError(
    val serverError: String? = null,
    val statusMessage: String? = null,
    val httpEx: Throwable? = null,
) : Throwable(serverError, httpEx)
