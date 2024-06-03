package com.server.auth.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.web.DefaultRedirectStrategy
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
    @ExceptionHandler(DataStorageException::class)
    fun handlerDataStorageException(ex: DataStorageException, request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any>? {
        request.session.setAttribute("EXCEPTION_HANDLER_LAST_EXCEPTION", ex)
        val defaultRedirectStrategy = DefaultRedirectStrategy()
        defaultRedirectStrategy.sendRedirect(request, response, "/error")
        return null
    }
}