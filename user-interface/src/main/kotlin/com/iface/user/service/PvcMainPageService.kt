package com.iface.user.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto


interface PvcMainPageService {

    fun getUserFileList(): List<PvcFileInfoDto>
    fun uploadFile(pvcIncomeData: () -> PvcFileDto)
    fun downloadFile(id: String): PvcFileDto
}