package main.controller

import main.dto.PvcFileDto
import main.dto.PvcFileInfoDto
import org.springframework.http.ResponseEntity

interface PvcFileController {

    fun downloadPvcFile(id: String): ResponseEntity<PvcFileDto>
    fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>>
    fun uploadPvcFile(pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto>
}