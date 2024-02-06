package com.storage.data.controller

import org.springframework.http.ResponseEntity
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.dto.PvcFileConstrainedDto

interface PvcFileController {

    fun downloadPvcFile(id: String): ResponseEntity<PvcFileDto>
    fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>>
    fun uploadPvcFile(pvcFile: PvcFileConstrainedDto): ResponseEntity<PvcFileInfoDto>
}