package com.iface.user.feign

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "PROCESSOR",
    configuration = [ProcessorClientConfiguration::class]
)
interface ProcessorClient {

    @GetMapping("\${pvc.processor.apiPath}/files")
    fun getFilesList(): List<PvcFileInfoDto>

    @PostMapping("\${pvc.processor.apiPath}/files")
    fun setFile(pvcFileDto: PvcFileDto): PvcFileInfoDto

    @GetMapping("\${pvc.processor.apiPath}/files/{id}")
    fun getFile(@PathVariable("id") id: String): PvcFileDto

}