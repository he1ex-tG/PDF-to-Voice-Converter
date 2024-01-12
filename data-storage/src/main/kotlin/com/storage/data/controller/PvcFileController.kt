package com.storage.data.controller

import org.springframework.http.ResponseEntity
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto

interface PvcFileController {

    fun downloadPvcFile(id: String): ResponseEntity<PvcFileDto>
    fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>>
    fun uploadPvcFile(pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto>
}