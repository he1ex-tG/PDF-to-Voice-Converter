package com.storage.data.main.service

import com.shared.objects.dto.PvcFileDto
import com.shared.objects.dto.PvcFileInfoDto


interface PvcFileService {

    fun savePvcFile(pvcFileDto: PvcFileDto): PvcFileInfoDto
    fun loadPvcFile(pvcFileId: String): PvcFileDto
    fun getPvcFileList(): List<PvcFileInfoDto>
}