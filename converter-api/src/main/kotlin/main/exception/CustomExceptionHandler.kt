package main.exception

import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.exceptions.IllegalPdfSyntaxException
import com.itextpdf.text.exceptions.InvalidPdfException
import com.itextpdf.text.exceptions.UnsupportedPdfException
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
    private fun buildResponseEntity(ex: CustomResponseEntityException): ResponseEntity<CustomResponseEntityException> {
        return ResponseEntity(ex, ex.status)
    }

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */

    /**
     * ITextPDF module exception handlers
     */
    @ExceptionHandler(BadPasswordException::class)
    fun handlerBadPasswordException(ex: BadPasswordException): ResponseEntity<CustomResponseEntityException> {
        val e = CustomResponseEntityException(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(IllegalPdfSyntaxException::class)
    fun handlerIllegalPdfSyntaxException(ex: IllegalPdfSyntaxException): ResponseEntity<CustomResponseEntityException> {
        val e = CustomResponseEntityException(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(InvalidPdfException::class)
    fun handlerInvalidPdfException(ex: InvalidPdfException): ResponseEntity<CustomResponseEntityException> {
        val e = CustomResponseEntityException(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception", ex)
        return buildResponseEntity(e)
    }

    @ExceptionHandler(UnsupportedPdfException::class)
    fun handlerUnsupportedPdfException(ex: UnsupportedPdfException): ResponseEntity<CustomResponseEntityException> {
        val e = CustomResponseEntityException(HttpStatus.BAD_REQUEST, ex.message ?: "PDF file exception", ex)
        return buildResponseEntity(e)
    }
}