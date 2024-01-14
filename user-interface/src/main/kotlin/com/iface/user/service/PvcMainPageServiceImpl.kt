package com.iface.user.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.stereotype.Service

@Service
class PvcMainPageServiceImpl : PvcMainPageService {
    override fun getFilesList(): List<PvcFileInfoDto> {
        return listOf()
    }

    override fun addFile(pvcIncomeData: () -> PvcFileDto) {

    }

    override fun getFile(id: String): PvcFileDto {
        return PvcFileDto("test", byteArrayOf())
    }
}