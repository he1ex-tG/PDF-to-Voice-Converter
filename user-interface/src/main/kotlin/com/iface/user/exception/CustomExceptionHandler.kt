package com.iface.user.exception

import com.objects.shared.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(ProcessorException::class)
    fun handlerConverterApiException(ex: ProcessorException, redirectAttributes: RedirectAttributes): String {
        val e = ApiException(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Processor module raised an exception",
            debugMessage = ex.message ?: "No debug message"
        )
        redirectAttributes.addFlashAttribute("apiException", e)
        return "redirect:/"
    }

}