package com.iface.user.feign

import com.iface.user.exception.ProcessorException
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager

class ProcessorClientConfiguration {

    @Value("\${pvc.authServer.appName}")
    lateinit var authServerAppName: String

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            val problemDetail = response.getProblemDetail()
            throw ProcessorException(problemDetail.status, problemDetail.detail)
        }
    }

    @Bean
    fun customRetryer(): Retryer {
        return CustomRetryer(
            maxAttempts = 3,
            backoff = 2000,
            pvcServiceException = ProcessorException(
                status = HttpStatus.SERVICE_UNAVAILABLE.value(),
                message = "Processor module does not respond to requests"
            )
        )
    }

    @Bean
    fun getOAuth2AccessTokenInterceptor(oAuth2AuthorizedClientManager: OAuth2AuthorizedClientManager): OAuth2AccessTokenInterceptor {
        return OAuth2AccessTokenInterceptor("$authServerAppName-client", oAuth2AuthorizedClientManager)
    }
}