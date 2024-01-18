package com.processor.feign

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "DataStorage",
    url = "localhost:7000/api/v1/files",
    configuration = [DataStorageClientConfiguration::class]
)
interface DataStorageClient {

    @GetMapping(path = ["/{id}"])
    fun getPvcFile(@PathVariable("id") id: String): PvcFileDto
    @GetMapping
    fun getPvcFilesList(): List<PvcFileInfoDto>
    @PostMapping
    fun sendPvcFile(@RequestBody pvcFile: PvcFileDto): PvcFileInfoDto
}