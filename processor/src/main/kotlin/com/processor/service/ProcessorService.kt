package com.processor.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto

interface ProcessorService {

    fun getFilesList(): List<PvcFileInfoDto>
    fun convertAndStoreFile(pvcFileDto: PvcFileDto): PvcFileInfoDto
    fun getFile(fileId: String): PvcFileDto
}