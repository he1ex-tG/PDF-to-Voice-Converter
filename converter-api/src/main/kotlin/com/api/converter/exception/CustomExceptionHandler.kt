package com.api.converter.exception

import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.exceptions.IllegalPdfSyntaxException
import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.exceptions.UnsupportedPdfException
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
    @ExceptionHandler(Throwable::class)
    fun handlerThrowable(ex: Throwable): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }

    /**
     * Custom exception handlers
     */

    /**
     * ITextPDF module exception handlers
     */
    @ExceptionHandler(BadPasswordException::class)
    fun handlerBadPasswordException(ex: BadPasswordException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.BAD_REQUEST.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(IllegalPdfSyntaxException::class)
    fun handlerIllegalPdfSyntaxException(ex: IllegalPdfSyntaxException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.BAD_REQUEST.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(InvalidPdfException::class)
    fun handlerInvalidPdfException(ex: InvalidPdfException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.BAD_REQUEST.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(UnsupportedPdfException::class)
    fun handlerUnsupportedPdfException(ex: UnsupportedPdfException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.BAD_REQUEST.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }
}