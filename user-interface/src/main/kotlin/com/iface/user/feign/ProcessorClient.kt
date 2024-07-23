package com.iface.user.feign

import com.objects.shared.controller.ProcessorMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "\${pvc.processor.appName}",
    path = "/api/v\${pvc.processor.apiVersion}",
    configuration = [ProcessorClientConfiguration::class]
)
interface ProcessorClient : ProcessorMainController