package com.processor.security

import com.objects.shared.security.PvcScopes
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RegexRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${pvc.authServer.address}")
    lateinit var authAddress: String
    @Value("\${pvc.authServer.port}")
    lateinit var authPort: String

    @Bean
    @Order(1)
    fun oauth2FilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            oauth2ResourceServer {
                jwt {
                    jwkSetUri = "$authAddress:$authPort/oauth2/v1/jwks"
                }
            }
            oauth2Client {  }
        }
        return http.build()
    }

    @Bean
    @Order(2)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                /**
                 * Files GET requests. Url like:
                 *  http://localhost:7015/api/v1/files
                 *  http://localhost:7015/api/v1/files/zzfilezz11337htg755zz
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.GET, "^/api/v[0-9]+/files(/[0-9a-z]+)?$"
                    ),
                    hasAuthority("SCOPE_${PvcScopes.USER.READ}")
                )
                /**
                 * Files POST requests. Url like:
                 *  http://localhost:7015/api/v1/files
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.POST, "^/api/v[0-9]+/files$"
                    ),
                    hasAuthority("SCOPE_${PvcScopes.USER.WRITE}")
                )
                authorize(anyRequest, denyAll)
            }
        }
        return http.build()
    }
}