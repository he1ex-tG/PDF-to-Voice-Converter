package com.iface.user.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.iface.user.exception.ProcessorException
import com.objects.shared.exception.ApiException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class ProcessorClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            val message: String
            try {
                val bodyInputStream = response.body().asInputStream()
                val apiException = ObjectMapper().readValue<ApiException>(bodyInputStream)
                message = apiException.message
            }
            catch (_: Exception) {
                throw ProcessorException(null)
            }
            throw ProcessorException(message)
        }
    }
}