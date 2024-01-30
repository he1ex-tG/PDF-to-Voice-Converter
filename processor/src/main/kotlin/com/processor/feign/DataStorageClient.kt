package com.processor.feign

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "DATA-STORAGE",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient {

    @GetMapping(path = ["\${pvc.dataStorage.apiPath}/files/{id}"])
    fun getPvcFile(@PathVariable("id") id: String): PvcFileDto

    @GetMapping(path = ["\${pvc.dataStorage.apiPath}/files"])
    fun getPvcFilesList(): List<PvcFileInfoDto>

    @PostMapping(path = ["\${pvc.dataStorage.apiPath}/files"])
    fun sendPvcFile(@RequestBody pvcFile: PvcFileDto): PvcFileInfoDto
}