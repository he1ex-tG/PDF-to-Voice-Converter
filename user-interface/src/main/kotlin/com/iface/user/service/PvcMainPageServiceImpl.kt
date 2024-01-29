package com.iface.user.service

import com.iface.user.feign.ProcessorClient
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.stereotype.Service

@Service
class PvcMainPageServiceImpl(
    private val processorClient: ProcessorClient
) : PvcMainPageService {

    override fun getFilesList(): List<PvcFileInfoDto> {
        return processorClient.getFilesList()
    }

    override fun setFile(pvcFileDto: PvcFileDto): PvcFileInfoDto {
        return processorClient.setFile(pvcFileDto)
    }

    override fun getFile(id: String): PvcFileDto {
        return processorClient.getFile(id)
    }
}