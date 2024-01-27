package com.processor.exception

import com.objects.shared.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */
    private fun buildResponseEntity(apiException: ApiException): ResponseEntity<ApiException> {
        return ResponseEntity(apiException, HttpStatus.valueOf(apiException.httpStatus))
    }

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ConverterApiException::class)
    fun handlerConverterApiException(ex: ConverterApiException): ResponseEntity<ApiException> {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Converter Api thrown an exception",
            debugMessage = ex.message ?: "No debug message"
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(DataStorageException::class)
    fun handlerDataStorageException(ex: DataStorageException): ResponseEntity<ApiException> {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Data Storage thrown an exception",
            debugMessage = ex.message ?: "No debug message"
        )
        return buildResponseEntity(e)
    }

}