package com.processor.feign

import com.processor.exception.ConverterApiException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class ConverterApiClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw ConverterApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Converter Api module does not respond to requests")
            }
            val problemDetail = response.getProblemDetail()
            throw ConverterApiException(problemDetail.status, problemDetail.detail)
        }
    }
}