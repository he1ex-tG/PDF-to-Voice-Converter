package com.server.auth.feign

import com.server.auth.exception.DataStorageException
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager

class DataStorageClientConfiguration {

    @Value("\${pvc.authServer.appName}")
    lateinit var authServerAppName: String

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            when (response.status()) {
                503 -> {
                    throw DataStorageException(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "Data Storage module does not respond to requests"
                    )
                }
                404 -> {
                    val problemDetail = response.getProblemDetail()
                    throw UsernameNotFoundException(problemDetail.detail)
                }
                else -> {
                    val problemDetail = response.getProblemDetail()
                    throw DataStorageException(problemDetail.status, problemDetail.detail)
                }
            }
        }
    }

    @Bean
    fun getOAuth2AccessTokenInterceptor(oAuth2AuthorizedClientManager: OAuth2AuthorizedClientManager): OAuth2AccessTokenInterceptor {
        return OAuth2AccessTokenInterceptor("${authServerAppName}-client", oAuth2AuthorizedClientManager)
    }
}