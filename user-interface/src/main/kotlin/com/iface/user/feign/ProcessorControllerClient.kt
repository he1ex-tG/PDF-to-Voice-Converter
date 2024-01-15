package com.iface.user.feign

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity

@FeignClient(
    name = "ProcessorController",
    url = "http://localhost:7015/api/v1"
)
interface ProcessorControllerClient {

    fun getFilesList(): ResponseEntity<List<PvcFileInfoDto>>

    fun setFile(pvcFileDto: PvcFileDto): ResponseEntity<PvcFileInfoDto>

    fun getFile(id: String): ResponseEntity<PvcFileDto>

}