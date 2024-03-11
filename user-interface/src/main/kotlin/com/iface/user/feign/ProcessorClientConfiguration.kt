package com.iface.user.feign

import com.iface.user.exception.ProcessorException
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class ProcessorClientConfiguration {

    @Bean
    fun customErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            if (response.status() == 503) {
                throw ProcessorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Processor module does not respond to requests")
            }
            val problemDetail = response.getProblemDetail()
            throw ProcessorException(problemDetail.status, problemDetail.detail)
        }
    }
}