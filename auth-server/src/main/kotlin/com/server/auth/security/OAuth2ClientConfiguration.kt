package com.server.auth.security

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

    @Value("\${pvc.authServer.oauth2Client.clientId}")
    lateinit var clientId: String
    @Value("\${pvc.authServer.oauth2Client.clientSecret}")
    lateinit var clientSecret: String
    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(pvcUserClientRegistration())
    }

    private fun pvcUserClientRegistration(): ClientRegistration {
        return ClientRegistration.withRegistrationId(appName)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("http://localhost:7020/login/oauth2/code/auth-client")
            .clientName("PVC auth-server client")
            .scope(
                "users:auth",
                "users:write",
                OidcScopes.OPENID
            )
            .authorizationUri("http://authserver:7020/oauth2/v1/authorize")
            .tokenUri("http://authserver:7020/oauth2/v1/token")
            .userInfoUri("http://authserver:7020/connect/v1/userinfo")
            .jwkSetUri("http://authserver:7020/oauth2/v1/jwks")
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .build()
    }
}