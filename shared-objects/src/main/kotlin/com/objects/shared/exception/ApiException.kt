package com.objects.shared.exception

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ApiException(
    val httpStatus: Int,
    val message: String,
    val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now()),
    val debugMessage: String = "",
    val subErrors: List<ApiValidationException>? = null,
) {
    constructor(
        httpStatus: Int,
        message: String,
        ex: Throwable
    ) : this(
        httpStatus = httpStatus,
        message = message,
        debugMessage = ex.localizedMessage
    )
}