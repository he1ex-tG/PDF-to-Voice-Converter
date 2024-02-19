package com.api.converter.exception

import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.exceptions.IllegalPdfSyntaxException
import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.exceptions.UnsupportedPdfException
import com.objects.shared.exception.ApiException
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */
    private fun buildResponseEntity(apiException: ApiException): ResponseEntity<Any> {
        val errorResponse = object : ErrorResponse {
            override fun getStatusCode(): HttpStatusCode {
                return HttpStatus.valueOf(apiException.httpStatus)
            }

            override fun getBody(): ProblemDetail {
                return object : ProblemDetail() {

                    override fun getStatus(): Int {
                        return apiException.httpStatus
                    }

                    override fun getTitle(): String {
                        return HttpStatus.valueOf(apiException.httpStatus).reasonPhrase
                    }

                    override fun getDetail(): String {
                        return ""
                    }

                    override fun getProperties(): MutableMap<String, Any> {
                        return mutableMapOf(ApiException::class.java.simpleName to apiException)
                    }
                }
            }

        }
        return ResponseEntity(errorResponse.body, errorResponse.headers, errorResponse.statusCode)
    }

    /**
     * Default exception handlers
     */

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Converter Api received incorrect input data",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(TtsEmptyStringException::class)
    fun handlerTtsEmptyStringException(ex: TtsEmptyStringException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Converter Api can not convert empty string to mp3",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    /**
     * ITextPDF module exception handlers
     */
    @ExceptionHandler(BadPasswordException::class)
    fun handlerBadPasswordException(ex: BadPasswordException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Converter Api received incorrect input data",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(IllegalPdfSyntaxException::class)
    fun handlerIllegalPdfSyntaxException(ex: IllegalPdfSyntaxException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Converter Api received incorrect input data",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(InvalidPdfException::class)
    fun handlerInvalidPdfException(ex: InvalidPdfException, request: WebRequest): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Converter Api received incorrect input data",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(UnsupportedPdfException::class)
    fun handlerUnsupportedPdfException(ex: UnsupportedPdfException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Converter Api received incorrect input data",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }
}