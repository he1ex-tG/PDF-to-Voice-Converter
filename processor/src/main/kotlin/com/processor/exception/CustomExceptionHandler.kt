package com.processor.exception

import com.objects.shared.exception.ApiException
import org.springframework.http.*
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

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ConverterApiException::class)
    fun handlerConverterApiException(ex: ConverterApiException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = "Converter Api module failed with details: ${ex.message ?: "No details"}; " +
                "and HttpStatus = ${ex.status ?: "Unknown"}"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(ConverterApiNotAvailable::class)
    fun handlerConverterApiNotAvailable(ex: ConverterApiNotAvailable, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.SERVICE_UNAVAILABLE
        val defaultDetail = "Converter Api module does not respond to requests"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(DataStorageException::class)
    fun handlerDataStorageException(ex: DataStorageException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = "Data Storage module failed with details: ${ex.message ?: "No details"}; " +
                "and HttpStatus = ${ex.status ?: "Unknown"}"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(DataStorageNotAvailable::class)
    fun handlerDataStorageNotAvailable(ex: DataStorageNotAvailable): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE.value(),
            message = "Data Storage module does not respond to requests",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

}