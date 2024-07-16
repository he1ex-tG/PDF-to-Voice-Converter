package com.iface.user.security

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

    @Value("\${pvc.ui.oauth2Client.clientId}")
    lateinit var clientId: String
    @Value("\${pvc.ui.oauth2Client.clientSecret}")
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
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("http://localhost:7010/login/oauth2/code/user-client")
            .clientName("PVC UI user client")
            .scope(
                "user:read",
                "user:write",
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