package com.iface.user.feign

import com.objects.shared.controller.ProcessorMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "PROCESSOR",
    path = "\${pvc.processor.apiPath}",
    configuration = [ProcessorClientConfiguration::class]
)
interface ProcessorClient : ProcessorMainController