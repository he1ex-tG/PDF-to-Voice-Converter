package com.processor.security

import com.processor.exception.ProcessorCurrentUserException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

class CurrentPvcUser {

    companion object {

        val id: String
            get() {
                val principal = SecurityContextHolder.getContext().authentication.principal
                if (principal !is Jwt) {
                    throw ProcessorCurrentUserException(message = "Principal is not equal to JWT")
                }
                if (!principal.hasClaim("dbid")) {
                    throw ProcessorCurrentUserException(message = "Principal claims do not contain \"dbid\" filed")
                }
                return principal.getClaimAsString("dbid")
            }
    }
}