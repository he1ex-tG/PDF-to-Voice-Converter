package com.processor.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames
import org.springframework.security.oauth2.core.oidc.OidcScopes

@Configuration
class OAuth2ClientConfiguration {

    @Value("\${pvc.processor.oauth2Client.clientId}")
    lateinit var clientId: String
    @Value("\${pvc.processor.oauth2Client.clientSecret}")
    lateinit var clientSecret: String
    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Value("\${pvc.authServer.address}")
    lateinit var authAddress: String
    @Value("\${pvc.authServer.port}")
    lateinit var authPort: String

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistrations: MutableList<ClientRegistration> = mutableListOf()
        clientRegistrations.apply {
            add(pvcUserClientRegistration())
        }
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }

    private fun pvcUserClientRegistration(): ClientRegistration {
        return ClientRegistration.withRegistrationId(appName)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("http://localhost:7015/login/oauth2/code/auth-client")
            .clientName("PVC processor client")
            .scope(
                "files:read",
                "files:write",
                OidcScopes.OPENID
            )
            .authorizationUri("$authAddress:$authPort/oauth2/v1/authorize")
            .tokenUri("$authAddress:$authPort/oauth2/v1/token")
            .userInfoUri("$authAddress:$authPort/connect/v1/userinfo")
            .jwkSetUri("$authAddress:$authPort/oauth2/v1/jwks")
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .build()
    }
}