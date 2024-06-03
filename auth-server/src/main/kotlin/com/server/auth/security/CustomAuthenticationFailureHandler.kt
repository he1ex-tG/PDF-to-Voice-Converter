package com.server.auth.security

import com.objects.shared.exception.PvcServiceException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class CustomAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        var curException: Throwable? = exception
        while (curException != null && curException != curException.cause && curException !is PvcServiceException) {
            curException = curException.cause
        }
        if (curException is PvcServiceException) {
            super.setDefaultFailureUrl("/error")
        } else {
            super.setDefaultFailureUrl(request.requestURI + "?error")
        }
        super.onAuthenticationFailure(request, response, exception)
    }
}