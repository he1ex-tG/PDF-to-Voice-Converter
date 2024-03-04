package com.storage.data.exception

import com.mongodb.MongoException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
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
    @ExceptionHandler(MongoException::class)
    fun handlerMongoException(ex: MongoException, request: WebRequest): ResponseEntity<Any>? {
        // Hide MongoDB exception messages
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = "Database exception"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val defaultDetail = ex.localizedMessage
/*
        for (constraintViolation in ex.constraintViolations) {
            val apiValidationException = ApiValidationException(
                constraintViolation.rootBeanClass.name,
                constraintViolation.message,
                constraintViolation.propertyPath.toString(),
                constraintViolation.invalidValue
            )
            e.subErrors.add(apiValidationException)
        }
*/
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

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
    @ExceptionHandler(SavePvcFileException::class)
    fun handlerSavePvcFileException(ex: SavePvcFileException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage ?: "Save file function thrown an exception, file is not saved"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    @ExceptionHandler(LoadPvcFileException::class)
    fun handlerLoadPvcFileException(ex: LoadPvcFileException, request: WebRequest): ResponseEntity<Any>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val defaultDetail = ex.localizedMessage ?: "Load file function thrown an exception, file is not loaded"
        val problem = createProblemDetail(ex, status, defaultDetail, null, null, request)
        val headers = HttpHeaders()
        return handleExceptionInternal(ex, problem, headers, status, request)
    }
}