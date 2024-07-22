package com.processor.exception

import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */

    /**
     * Default exception handlers
     */
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ConverterApiException::class)
    fun handlerConverterApiException(ex: ConverterApiException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(DataStorageException::class)
    fun handlerDataStorageException(ex: DataStorageException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(ProcessorCurrentUserException::class)
    fun handlerProcessorGetCurrentUserException(ex: ProcessorCurrentUserException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }
}