package main.controller

import main.dto.PvcFileDto
import main.dto.PvcFileInfoDto

interface PvcFileController {

    fun downloadPvcFile(id: String): PvcFileDto
    fun downloadPvcFileList(): List<PvcFileInfoDto>
    fun uploadPvcFile(pvcFile: PvcFileDto): PvcFileInfoDto
}