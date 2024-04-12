package com.iface.user.feign

import com.iface.user.exception.ProcessorException
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager

class ProcessorClientConfiguration {

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw ProcessorException(
                    HttpStatus.SERVICE_UNAVAILABLE.value(),
                    "Processor module does not respond to requests"
                )
            }
            val problemDetail = response.getProblemDetail()
            throw ProcessorException(problemDetail.status, problemDetail.detail)
        }
    }

    @Bean
    fun getOAuth2AccessTokenInterceptor(oAuth2AuthorizedClientManager: OAuth2AuthorizedClientManager): OAuth2AccessTokenInterceptor {
        return OAuth2AccessTokenInterceptor(appName, oAuth2AuthorizedClientManager)
    }
}