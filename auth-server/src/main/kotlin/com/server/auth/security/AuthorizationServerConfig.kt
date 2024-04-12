package com.server.auth.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*


@Configuration
class AuthorizationServerConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails: UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(userDetails)
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        return InMemoryRegisteredClientRepository(
            getUserClientRegisteredClient(),
            //getUserRegisteredClient()
        )
    }

    private fun getUserClientRegisteredClient(): RegisteredClient {
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("user-client")
            .clientSecret("{noop}user-client-password")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://localhost:7010/login/oauth2/code/user-client")
            .postLogoutRedirectUri("http://localhost:7010/")
            .scopes {
                it.add("user:read")
                it.add("user:write")
                it.add(OidcScopes.OPENID)
            }
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }

/*
    private fun getUserRegisteredClient(): RegisteredClient {
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("taco-user-client")
            .clientSecret(encoder.encode("taco-user-client"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://localhost:8080/login/oauth2/code/taco-user-client")
            .scope("taco:user")
            .scope(OidcScopes.OPENID)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }
*/

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaKey = generateRsa()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun generateRsa(): RSAKey {
        val keyPair = generateRsaKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        return RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
    }

    private fun generateRsaKey(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder()
            .authorizationEndpoint("/oauth2/v1/authorize")
            .deviceAuthorizationEndpoint("/oauth2/v1/device_authorization")
            .deviceVerificationEndpoint("/oauth2/v1/device_verification")
            .tokenEndpoint("/oauth2/v1/token")
            .tokenIntrospectionEndpoint("/oauth2/v1/introspect")
            .tokenRevocationEndpoint("/oauth2/v1/revoke")
            .jwkSetEndpoint("/oauth2/v1/jwks")
            .oidcLogoutEndpoint("/connect/v1/logout")
            .oidcUserInfoEndpoint("/connect/v1/userinfo")
            .oidcClientRegistrationEndpoint("/connect/v1/register")
            .build()
    }
}