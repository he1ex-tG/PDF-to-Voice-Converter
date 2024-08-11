package com.iface.user.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class CustomExceptionHandler {

    /**
     * ResponseEntity builder
     */

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ProcessorException::class)
    fun handlerProcessorException(ex: ProcessorException, request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any>? {
        response.sendError(
            ex.status ?: 999,
            ex.message ?: "No message"
        )
        return null
    }
}