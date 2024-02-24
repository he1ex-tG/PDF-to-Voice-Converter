package com.processor.feign

import com.objects.shared.controller.ConverterApiMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "CONVERTER-API",
    path = "\${pvc.converterApi.apiPath}",
    configuration = [ConverterApiClientConfiguration::class]
)
interface ConverterApiClient : ConverterApiMainController