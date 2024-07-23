package com.server.auth.feign

import com.objects.shared.controller.DataStorageUserController
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
    name = "\${pvc.dataStorage.appName}",
    path = "/api/v\${pvc.dataStorage.apiVersion}",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient : DataStorageUserController