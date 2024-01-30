package com.processor.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "CONVERTER-API",
    configuration = [ConverterApiClientConfiguration::class]
)
interface ConverterApiClient {

    @PostMapping("\${pvc.converterApi.apiPath}/converter")
    fun convert(data: ByteArray): ByteArray
}