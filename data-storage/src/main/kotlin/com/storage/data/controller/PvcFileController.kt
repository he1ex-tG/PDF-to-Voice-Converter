package com.storage.data.controller

import org.springframework.http.ResponseEntity
import com.shared.objects.dto.PvcFileDto
import com.shared.objects.dto.PvcFileInfoDto

interface PvcFileController {

    fun downloadPvcFile(id: String): ResponseEntity<PvcFileDto>
    fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>>
    fun uploadPvcFile(pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto>
}