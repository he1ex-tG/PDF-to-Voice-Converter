package com.server.auth.exception

import com.objects.shared.exception.PvcServiceException
import jakarta.servlet.http.HttpSession
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class CustomErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val session = webRequest.sessionMutex
        if (session is HttpSession) {
            var exception = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") as Throwable?
            while (exception != null && exception != exception.cause && exception !is PvcServiceException) {
                exception = exception.cause
            }
            if (exception is PvcServiceException) {
                val exceptionStatus = exception.status
                if (exceptionStatus != null) {
                    webRequest.setAttribute("jakarta.servlet.error.status_code", exceptionStatus, 0)
                }
                val exceptionMessage = exception.message
                if (exceptionMessage != null) {
                    webRequest.setAttribute("jakarta.servlet.error.message", exceptionMessage, 0)
                }
                webRequest.setAttribute("jakarta.servlet.error.request_uri", "", 0)
                webRequest.setAttribute("jakarta.servlet.error.exception", exception, 0)
            }
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION")
        }
        return super.getErrorAttributes(webRequest, options)
    }
}