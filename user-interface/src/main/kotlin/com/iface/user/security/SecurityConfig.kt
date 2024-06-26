package com.iface.user.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize(HttpMethod.GET,"/error", permitAll)
                authorize(HttpMethod.GET,"/**", hasAuthority("SCOPE_user:read"))
                authorize(HttpMethod.POST,"/**", hasAuthority("SCOPE_user:write"))
            }
            oauth2Login {
                defaultSuccessUrl("/", true)
            }
        }
        return http.build()
    }
}