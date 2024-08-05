package com.iface.user.security

import com.objects.shared.configuration.PvcConfiguration
import com.objects.shared.security.PvcScopes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.ClientRegistrations
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes

@Configuration
@Import(PvcConfiguration::class)
class OAuth2ClientConfiguration(
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
        return ClientRegistrations.fromOidcIssuerLocation("${pvcConfiguration.authServer.address}:${pvcConfiguration.authServer.port}")
            .registrationId(pvcConfiguration.userInterface.appName)
            .clientId(pvcConfiguration.userInterface.oAuth2Client.clientId)
            .clientSecret(pvcConfiguration.userInterface.oAuth2Client.clientSecret)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("${pvcConfiguration.userInterface.address}:${pvcConfiguration.userInterface.port}/login/oauth2/code/${pvcConfiguration.userInterface.appName}")
            .clientName("PVC UI user client")
            .scope(
                PvcScopes.USER.READ,
                PvcScopes.USER.WRITE,
                OidcScopes.OPENID
            )
            .build()
    }
}