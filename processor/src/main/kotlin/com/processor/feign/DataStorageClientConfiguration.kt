package com.processor.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.processor.exception.DataStorageException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail

class DataStorageClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw DataStorageException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data Storage module does not respond to requests")
            }
            val problemDetail = getProblemDetail(response)
            throw DataStorageException(problemDetail.status, problemDetail.detail)
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