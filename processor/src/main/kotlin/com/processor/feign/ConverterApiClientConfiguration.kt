package com.processor.feign

import com.processor.exception.ConverterApiException
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class ConverterApiClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            val problemDetail = response.getProblemDetail()
            throw ConverterApiException(problemDetail.status, problemDetail.detail)
        }
    }

    @Bean
    fun customRetryer(): Retryer {
        return CustomRetryer(
            maxAttempts = 3,
            backoff = 2000,
            pvcServiceException = ConverterApiException(
                status = HttpStatus.SERVICE_UNAVAILABLE.value(),
                message = "Converter Api module does not respond to requests"
            )
        )
    }
}