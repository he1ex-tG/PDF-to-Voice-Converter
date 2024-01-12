package com.iface.user.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.stereotype.Service

@Service
class PvcMainPageServiceImpl : PvcMainPageService {
    override fun getUserFileList(): List<PvcFileInfoDto> {
        return listOf()
    }

    override fun uploadFile(pvcIncomeData: () -> PvcFileDto) {

    }

    override fun downloadFile(id: String): PvcFileDto {
        return PvcFileDto("test", byteArrayOf())
    }
}