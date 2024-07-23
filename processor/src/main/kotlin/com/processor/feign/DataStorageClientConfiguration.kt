package com.processor.feign

import com.processor.exception.DataStorageException
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager

class DataStorageClientConfiguration {

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw DataStorageException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data Storage module does not respond to requests")
            }
            val problemDetail = response.getProblemDetail()
            throw DataStorageException(problemDetail.status, problemDetail.detail)
        }
    }

    @Bean
    fun getOAuth2AccessTokenInterceptor(oAuth2AuthorizedClientManager: OAuth2AuthorizedClientManager): OAuth2AccessTokenInterceptor {
        return OAuth2AccessTokenInterceptor(appName, oAuth2AuthorizedClientManager)
    }
}