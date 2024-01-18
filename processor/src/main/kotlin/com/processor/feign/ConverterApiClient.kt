package com.processor.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "ConverterApi",
    url = "localhost:7005/api/v1/converter",
    configuration = [ConverterApiClientConfiguration::class]
)
interface ConverterApiClient {

    @PostMapping
    fun convert(data: ByteArray): ByteArray
}