package main.service

import main.dto.PvcFileDto
import main.dto.PvcFileInfoDto

interface PvcFileService {

    fun savePvcFile(pvcFileDto: PvcFileDto): PvcFileInfoDto
    fun loadPvcFile(pvcFileId: String): PvcFileDto
    fun getPvcFileList(): List<PvcFileInfoDto>
}