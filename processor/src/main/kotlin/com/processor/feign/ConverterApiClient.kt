package com.processor.feign

import com.objects.shared.controller.ConverterApiMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "\${pvc.converterApi.appName}",
    url = "\${pvc.converterApi.uri}/api/v\${pvc.converterApi.apiVersion}",
    configuration = [ConverterApiClientConfiguration::class]
)
interface ConverterApiClient : ConverterApiMainController