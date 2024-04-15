package com.processor.feign

import com.objects.shared.controller.DataStorageFileController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "DATA-STORAGE",
    path = "/api/v\${pvc.dataStorage.apiVersion}",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient : DataStorageFileController