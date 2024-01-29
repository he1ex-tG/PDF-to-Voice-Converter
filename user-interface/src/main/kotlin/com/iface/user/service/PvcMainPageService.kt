package com.iface.user.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto


interface PvcMainPageService {

    fun getFilesList(): List<PvcFileInfoDto>

    fun setFile(pvcFileDto: PvcFileDto): PvcFileInfoDto

    fun getFile(id: String): PvcFileDto
}