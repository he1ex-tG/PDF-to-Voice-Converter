package com.iface.user.feign

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "Processor",
    url = "http://localhost:7015/api/v1/files",
    configuration = [ProcessorClientConfiguration::class]
)
interface ProcessorClient {

    @GetMapping
    fun getFilesList(): List<PvcFileInfoDto>

    @PostMapping
    fun setFile(pvcFileDto: PvcFileDto): PvcFileInfoDto

    @GetMapping("/{id}")
    fun getFile(@PathVariable("id") id: String): PvcFileDto

}