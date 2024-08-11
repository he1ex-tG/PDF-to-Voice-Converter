package com.server.auth.exception

import com.objects.shared.exception.PvcServiceException
import jakarta.servlet.http.HttpSession
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest
import org.springframework.web.util.WebUtils
import java.util.Enumeration

@Component
class CustomErrorAttributes : DefaultErrorAttributes() {

    private val exceptionAttributesNames = listOf(
        /**
         * Exceptions which was generated by Spring Security
         */
        "SPRING_SECURITY_LAST_EXCEPTION",
        /**
         * Exceptions which was generated by Exception Handler
         */
        "EXCEPTION_HANDLER_LAST_EXCEPTION"
    )

    private fun presentExceptionAttributeName(attributes: Enumeration<String>): String? {
        var presentAttribute: String? = null
        while (attributes.hasMoreElements()) {
            val nextElement = attributes.nextElement()
            if (exceptionAttributesNames.contains(nextElement)) {
                presentAttribute = nextElement
                break
            }
        }
        return presentAttribute
    }

    override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val session = webRequest.sessionMutex
        if (session is HttpSession) {
            val presentExceptionAttributeName = presentExceptionAttributeName(session.attributeNames)
            var exception = session.getAttribute(presentExceptionAttributeName) as Throwable?
            while (exception != null && exception != exception.cause && exception !is PvcServiceException) {
                exception = exception.cause
            }
            if (exception is PvcServiceException) {
                webRequest.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, exception.status ?: "", 0)
                webRequest.setAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE, exception.message ?: "", 0)
                webRequest.setAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE, webRequest.getHeader(HttpHeaders.REFERER) ?: "", 0)
                webRequest.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, 0)
            }
            session.removeAttribute(presentExceptionAttributeName)
        }
        return super.getErrorAttributes(webRequest, options)
    }
}