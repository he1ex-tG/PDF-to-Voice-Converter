package com.iface.user.feign

import com.objects.shared.controller.ProcessorMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "\${pvc.processor.appName}",
    url = "\${pvc.processor.uri}/api/v\${pvc.processor.apiVersion}",
    configuration = [ProcessorClientConfiguration::class]
)
interface ProcessorClient : ProcessorMainController