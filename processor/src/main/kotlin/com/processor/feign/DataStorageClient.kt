package com.processor.feign

import com.objects.shared.controller.DataStorageMainController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "DATA-STORAGE",
    path = "\${pvc.dataStorage.apiPath}",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient : DataStorageMainController