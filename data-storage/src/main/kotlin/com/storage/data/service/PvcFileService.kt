package com.storage.data.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto


interface PvcFileService {

    fun savePvcFile(pvcFileDto: PvcFileDto): PvcFileInfoDto

    fun loadPvcFile(pvcFileId: String): PvcFileDto

    fun getPvcFileList(): List<PvcFileInfoDto>

}