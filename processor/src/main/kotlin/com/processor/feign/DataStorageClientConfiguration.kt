package com.processor.feign

import com.processor.exception.ConverterApiException
import com.processor.exception.DataStorageException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class DataStorageClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            val body = response.body()
            if (body is Throwable) {
                throw DataStorageException(body.message ?: "")
            }
            throw DataStorageException(null)
        }
    }
}