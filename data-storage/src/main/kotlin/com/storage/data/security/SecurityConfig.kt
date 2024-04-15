package com.storage.data.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RegexRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${pvc.dataStorage.filesEndpoint}")
    lateinit var filesEndpoint: String
    @Value("\${pvc.dataStorage.usersEndpoint}")
    lateinit var usersEndpoint: String
    @Value("\${pvc.authServer.address}")
    lateinit var authAddress: String
    @Value("\${pvc.authServer.port}")
    lateinit var authPort: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.GET,
                        "/api/v[0-9]+$filesEndpoint/.+"
                    ),
                    hasAuthority("SCOPE_files:read")
                )
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.POST,
                        "/api/v[0-9]+$filesEndpoint/.+"
                    ),
                    hasAuthority("SCOPE_files:write")
                )
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.GET,
                        "/api/v[0-9]+$usersEndpoint/.+"
                    ),
                    hasAuthority("SCOPE_users:read")
                )
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.POST,
                        "/api/v[0-9]+$usersEndpoint/.+"
                    ),
                    hasAuthority("SCOPE_users:write")
                )
                authorize(anyRequest, denyAll)
            }
            oauth2ResourceServer {
                jwt {
                    jwkSetUri = "$authAddress:$authPort/oauth2/v1/jwks"
                }
            }
        }
        return http.build()
    }
}