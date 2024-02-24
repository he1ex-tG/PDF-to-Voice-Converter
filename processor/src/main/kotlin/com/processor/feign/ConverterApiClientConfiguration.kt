package com.processor.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.processor.exception.ConverterApiException
import com.processor.exception.ConverterApiNotAvailable
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.ProblemDetail

class ConverterApiClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw ConverterApiNotAvailable()
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