package com.objects.shared.exception

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ApiException(
    @JsonProperty("httpStatus")
    val httpStatus: Int,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("timestamp")
    val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now()),
    @JsonProperty("debugMessage")
    val debugMessage: String = "",
    @JsonProperty("subErrors")
    val subErrors: MutableList<ApiValidationException> = mutableListOf(),
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