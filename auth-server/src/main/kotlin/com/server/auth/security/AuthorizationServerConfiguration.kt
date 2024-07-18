package com.server.auth.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.objects.shared.configuration.PvcConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
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
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

@Configuration
@Import(PvcConfiguration::class)
class AuthorizationServerConfiguration(
    private val pvcConfiguration: PvcConfiguration
) {

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        val registeredClients: MutableList<RegisteredClient> = mutableListOf()
        registeredClients.apply {
            add(getUserClientRegisteredClient())
            add(getAuthClientRegisteredClient())
            add(getProcessorClientRegisteredClient())
        }
        return InMemoryRegisteredClientRepository(registeredClients)
    }

    private fun getUserClientRegisteredClient(): RegisteredClient {
        val uiConfig = pvcConfiguration.userInterface
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(uiConfig.oAuth2Client.clientId)
            .clientSecret("{noop}${uiConfig.oAuth2Client.clientSecret}")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("${uiConfig.address}:${uiConfig.port}/login/oauth2/code/user-client")
            .postLogoutRedirectUri("${uiConfig.address}:${uiConfig.port}/")
            .scopes {
                it.add("user:read")
                it.add("user:write")
                it.add(OidcScopes.OPENID)
            }
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }

    private fun getAuthClientRegisteredClient(): RegisteredClient {
        val authConfig = pvcConfiguration.authServer
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(authConfig.oAuth2Client.clientId)
            .clientSecret("{noop}${authConfig.oAuth2Client.clientSecret}")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("${authConfig.address}:${authConfig.port}/login/oauth2/code/auth-client")
            .postLogoutRedirectUri("${authConfig.address}:${authConfig.port}/")
            .scopes {
                it.add("auth:auth")
                it.add("auth:write")
                it.add(OidcScopes.OPENID)
            }
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }

    private fun getProcessorClientRegisteredClient(): RegisteredClient {
        val procConfig = pvcConfiguration.processor
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(procConfig.oAuth2Client.clientId)
            .clientSecret("{noop}${procConfig.oAuth2Client.clientSecret}")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("${procConfig.address}:${procConfig.port}/login/oauth2/code/auth-client")
            .postLogoutRedirectUri("${procConfig.address}:${procConfig.port}/")
            .scopes {
                it.add("files:read")
                it.add("files:write")
                it.add(OidcScopes.OPENID)
            }
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }

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

    /**
     * Create Authorization Server with custom paths
     */
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