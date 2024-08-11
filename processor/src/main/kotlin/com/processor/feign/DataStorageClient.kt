package com.processor.feign

import com.objects.shared.controller.DataStorageFileController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "\${pvc.dataStorage.appName}",
    url = "\${pvc.dataStorage.uri}/api/v\${pvc.dataStorage.apiVersion}",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient : DataStorageFileController