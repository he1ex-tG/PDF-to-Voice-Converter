package com.processor.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.http.ResponseEntity

interface IncomePdfController {

    fun getFilesList(): ResponseEntity<List<PvcFileInfoDto>>
    fun addFile(pvcFileDto: PvcFileDto)
    fun getFile(id: String): ResponseEntity<PvcFileDto>
}