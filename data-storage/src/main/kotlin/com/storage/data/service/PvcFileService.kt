package com.storage.data.service

import com.shared.objects.dto.PvcFileDto
import com.shared.objects.dto.PvcFileInfoDto


interface PvcFileService {

    fun savePvcFile(pvcFileDto: PvcFileDto): PvcFileInfoDto
    fun loadPvcFile(pvcFileId: String): PvcFileDto
    fun getPvcFileList(): List<PvcFileInfoDto>
}