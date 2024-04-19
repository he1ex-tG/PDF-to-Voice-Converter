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

    @Value("\${pvc.authServer.address}")
    lateinit var authAddress: String
    @Value("\${pvc.authServer.port}")
    lateinit var authPort: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                /**
                 * Files GET requests. Url like:
                 *  http://localhost:7010/api/v1/users/aauseraa11337htg755aa/files
                 *  http://localhost:7010/api/v1/users/aauseraa11337htg755aa/files/zzfilezz11337htg755zz
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.GET, "^/api/v[0-9]+/users/[0-9a-z]+/files(/[0-9a-z]+)?$"
                    ),
                    hasAuthority("SCOPE_files:read")
                )
                /**
                 * Files POST requests. Url like:
                 *  http://localhost:7010/api/v1/users/aauseraa11337htg755aa/files
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.POST, "^/api/v[0-9]+/users/[0-9a-z]+/files$"
                    ),
                    hasAuthority("SCOPE_files:write")
                )
                /**
                 * User auth GET requests. Url like:
                 *  http://localhost:7010/api/v1/auth/aa_USER_aa11337-HTG-755aa
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.GET, "^/api/v[0-9]+/auth/[0-9A-Za-z_-]+$"
                    ),
                    hasAuthority("SCOPE_users:auth")
                )
                /**
                 * User POST requests. Url like:
                 *  http://localhost:7010/api/v1/users
                 */
                authorize(
                    RegexRequestMatcher.regexMatcher(
                        HttpMethod.POST, "^/api/v[0-9]+/users$"
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