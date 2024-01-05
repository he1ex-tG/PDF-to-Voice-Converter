package main.exception

import org.springframework.http.HttpStatus
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CustomResponseEntityException(
    val status: HttpStatus,
    val message: String,
    val timestamp: String = DateTimeFormatter
        .ofPattern("HH:mm:ss.SSSSSS dd.MM.yyyy")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now()),
    val debugMessage: String = ""
) {
    constructor(
        status: HttpStatus,
        message: String,
        ex: Throwable
    ) : this(
        status = status,
        message = message,
        debugMessage = ex.localizedMessage
    )
}