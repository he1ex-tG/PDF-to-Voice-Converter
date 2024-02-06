package com.storage.data.exception

import com.mongodb.MongoException
import com.objects.shared.exception.ApiException
import com.objects.shared.exception.ApiValidationException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.ErrorResponse
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

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Data Storage received incorrect input data",
            debugMessage = ""
        )
        return buildResponseEntity(e)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Any> {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Data Storage received incorrect input data",
            debugMessage = ""
        )
        for (constraintViolation in ex.constraintViolations) {
            val apiValidationException = ApiValidationException(
                constraintViolation.rootBeanClass.name,
                constraintViolation.message,
                constraintViolation.propertyPath.toString(),
                constraintViolation.invalidValue
            )
            e.subErrors.add(apiValidationException)
        }
        return buildResponseEntity(e)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Data Storage received incorrect input data",
            debugMessage = "",
        )
        for (er in ex.bindingResult.fieldErrors) {
            e.subErrors.add(
                ApiValidationException(er.objectName, er.defaultMessage.orEmpty(), er.field, er.rejectedValue)
            )
        }
        return buildResponseEntity(e)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val e = ApiException(
            httpStatus = HttpStatus.BAD_REQUEST.value(),
            message = "Data Storage received incorrect input data",
            debugMessage = ex.message.orEmpty()
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