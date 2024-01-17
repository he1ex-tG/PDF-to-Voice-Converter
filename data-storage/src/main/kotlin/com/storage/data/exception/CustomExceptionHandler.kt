package com.storage.data.exception

import com.mongodb.MongoException
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

    @ExceptionHandler(MongoException::class)
    fun handlerMongoException(ex: MongoException): ResponseEntity<ApiException> {
        // Hide MongoDB exception messages
        val templateEx = Throwable("Unknown database exception")
        val e = ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), templateEx.message ?: "No message", templateEx)
        return buildResponseEntity(e)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(SavePvcFileException::class)
    fun handlerSavePvcFileException(ex: SavePvcFileException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(LoadPvcFileException::class)
    fun handlerLoadPvcFileException(ex: LoadPvcFileException): ResponseEntity<ApiException> {
        val e = ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "No message", ex)
        return buildResponseEntity(e)
    }
}