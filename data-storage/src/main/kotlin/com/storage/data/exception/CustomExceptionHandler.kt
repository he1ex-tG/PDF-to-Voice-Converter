package com.storage.data.exception

import com.mongodb.MongoException
import com.objects.shared.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
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
                        return mutableMapOf(apiException::class.java.simpleName to apiException)
                    }
                }
            }

        }
        return ResponseEntity(errorResponse.body, errorResponse.headers, errorResponse.statusCode)
    }

    /**
     * Default exception handlers
     */

    @ExceptionHandler(MongoException::class)
    fun handlerMongoException(ex: MongoException): ResponseEntity<Any> {
        // Hide MongoDB exception messages
        val templateEx = Throwable("Database exception")
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Data Storage module encountered an exception",
            debugMessage = templateEx.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(SavePvcFileException::class)
    fun handlerSavePvcFileException(ex: SavePvcFileException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Data Storage module encountered an exception when saving a file",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(LoadPvcFileException::class)
    fun handlerLoadPvcFileException(ex: LoadPvcFileException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Data Storage module encountered an exception when loading a file",
            debugMessage = ex.message.orEmpty()
        )
        return buildResponseEntity(e)
    }
}