package com.server.auth.security

import com.objects.shared.configuration.PvcConfiguration
import com.objects.shared.security.PvcScopes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings

@Configuration
@Import(PvcConfiguration::class)
class OAuth2ClientConfiguration(
    private val authorizationServerSettings: AuthorizationServerSettings,
    private val pvcConfiguration: PvcConfiguration
) {

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistrations: MutableList<ClientRegistration> = mutableListOf()
        clientRegistrations.apply {
            add(pvcUserClientRegistration())
        }
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }

    private fun pvcUserClientRegistration(): ClientRegistration {
        return ClientRegistration.withRegistrationId("${pvcConfiguration.authServer.appName}-client")
            .clientId(pvcConfiguration.authServer.oAuth2Client.clientId)
            .clientSecret(pvcConfiguration.authServer.oAuth2Client.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope(
                PvcScopes.AUTH.AUTH,
                PvcScopes.AUTH.WRITE,
                OidcScopes.OPENID
            )
            .tokenUri("${pvcConfiguration.authServer.uri}${authorizationServerSettings.tokenEndpoint}")
            .build()
    }
}