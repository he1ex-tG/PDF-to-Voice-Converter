package com.iface.user.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
@ConfigurationProperties(prefix = "pvc")
class OAuth2ClientConfiguration {

    lateinit var oauth2: Map<String, String>

    @Bean
    open fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(googleClientRegistration())
    }

    private fun googleClientRegistration(): ClientRegistration {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
            .clientId(oauth2["clientId"])
            .clientSecret(oauth2["clientSecret"])
            .scope("user")
            .build()
    }
}