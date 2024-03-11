package com.processor.feign

import com.processor.exception.DataStorageException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class DataStorageClientConfiguration {

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
}