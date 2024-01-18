package com.processor.feign

import com.processor.exception.ConverterApiException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class ConverterApiClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, _ ->
            throw ConverterApiException("Converter Api module responded something else instead of OK")
        }
    }
}