package com.processor.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.processor.exception.ConverterApiException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail

class ConverterApiClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw ConverterApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Converter Api module does not respond to requests")
            }
            val problemDetail = getProblemDetail(response)
            throw ConverterApiException(problemDetail.status, problemDetail.detail)
        }
    }

    private fun getProblemDetail(response: Response): ProblemDetail {
        return response.body().asInputStream().use {
            val mapper = ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            mapper.readValue(it, ProblemDetail::class.java)
        }
    }
}