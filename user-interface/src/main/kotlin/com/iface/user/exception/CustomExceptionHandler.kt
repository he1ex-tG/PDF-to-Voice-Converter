package com.iface.user.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ProcessorException::class)
    fun handlerProcessorException(ex: ProcessorException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }
}