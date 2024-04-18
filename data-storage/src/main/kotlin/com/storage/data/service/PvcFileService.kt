package com.storage.data.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto


interface PvcFileService {

    fun savePvcFile(pvcUserId: String, pvcFileDto: PvcFileDto): PvcFileInfoDto

    fun loadPvcFile(pvcUserId: String, pvcFileId: String): PvcFileDto

    fun getPvcFileList(pvcUserId: String): List<PvcFileInfoDto>

    fun deletePvcFile(pvcUserId: String, pvcFileId: String)

}