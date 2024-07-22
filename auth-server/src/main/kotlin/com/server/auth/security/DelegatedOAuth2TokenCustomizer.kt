package com.server.auth.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.stereotype.Component

@Component
class DelegatedOAuth2TokenCustomizer : OAuth2TokenCustomizer<JwtEncodingContext> {

    private fun extractNewClaims(authentication: Authentication): Map<String, String> {
        if (authentication !is UsernamePasswordAuthenticationToken || authentication.principal !is CustomUserDetails) {
            return emptyMap()
        }
        val customUserDetails = authentication.principal as CustomUserDetails
        val newClaims = mutableMapOf<String, String>()
        newClaims.apply {
            put("dbid", customUserDetails.dbid)
        }
        return newClaims
    }

    override fun customize(context: JwtEncodingContext) {
        val newClaims = extractNewClaims(context.getPrincipal())
        context.claims.claims { claim ->
            newClaims.forEach { principalClaim ->
                claim[principalClaim.key] = principalClaim.value
            }
        }
    }
}