package com.storage.data.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto


interface PvcFileService {

    fun savePvcFile(pvcFileDto: PvcFileDto, pvcUserId: String): PvcFileInfoDto

    fun loadPvcFile(pvcFileId: String, pvcUserId: String): PvcFileDto

    fun getPvcFileList(pvcUserId: String): List<PvcFileInfoDto>

    fun deletePvcFile(pvcFileId: String, pvcUserId: String)

}