package com.processor.feign

import com.processor.exception.DataStorageException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class DataStorageClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, _ ->
            throw DataStorageException("Data Storage module responded something else instead of OK")
        }
    }
}