package com.iface.user.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.ModelAndView

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
    fun handlerProcessorException(ex: ProcessorException, request: ServletWebRequest): ModelAndView {
        val issuerUri = request.request.requestURI
        return ModelAndView().apply {
            viewName = "error"
            addObject("status", ex.status ?: "No status")
            addObject("message", ex.message ?: "No message")
            addObject("issuerUri", issuerUri ?: "/")
        }
    }
}