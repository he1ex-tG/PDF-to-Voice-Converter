package com.api.converter.exception

import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.exceptions.IllegalPdfSyntaxException
import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.exceptions.UnsupportedPdfException
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
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
    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(TtsEmptyStringException::class)
    fun handlerTtsEmptyStringException(ex: TtsEmptyStringException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage ?: "Text is empty or consists solely of whitespace characters"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    /**
     * ITextPDF module exception handlers
     */
    @ExceptionHandler(BadPasswordException::class)
    fun handlerBadPasswordException(ex: BadPasswordException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(IllegalPdfSyntaxException::class)
    fun handlerIllegalPdfSyntaxException(ex: IllegalPdfSyntaxException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(InvalidPdfException::class)
    fun handlerInvalidPdfException(ex: InvalidPdfException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(UnsupportedPdfException::class)
    fun handlerUnsupportedPdfException(ex: UnsupportedPdfException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }
}