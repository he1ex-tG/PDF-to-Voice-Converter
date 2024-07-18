package com.server.auth.security

import com.objects.shared.configuration.PvcConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames
import org.springframework.security.oauth2.core.oidc.OidcScopes

@Configuration
@Import(PvcConfiguration::class)
class OAuth2ClientConfiguration(
    private val pvcConfiguration: PvcConfiguration
) {

    @Value("\${spring.application.name}")
    lateinit var authAppName: String

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistrations: MutableList<ClientRegistration> = mutableListOf()
        clientRegistrations.apply {
            add(pvcUserClientRegistration())
        }
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }

    private fun pvcUserClientRegistration(): ClientRegistration {
        return ClientRegistration.withRegistrationId(authAppName)
            .clientId(pvcConfiguration.authServer.oAuth2Client.clientId)
            .clientSecret(pvcConfiguration.authServer.oAuth2Client.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}/login/oauth2/code/auth-client")
            .clientName("PVC auth-server client")
            .scope(
                "auth:auth",
                "auth:write",
                OidcScopes.OPENID
            )
            .authorizationUri("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}/oauth2/v1/authorize")
            .tokenUri("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}/oauth2/v1/token")
            .userInfoUri("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}/connect/v1/userinfo")
            .jwkSetUri("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}/oauth2/v1/jwks")
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .build()
    }
}