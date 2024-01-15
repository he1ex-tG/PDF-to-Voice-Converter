package com.iface.user.service

import com.iface.user.feign.ProcessorControllerClient
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.stereotype.Service

@Service
class PvcMainPageServiceImpl(
    private val processorControllerClient: ProcessorControllerClient
) : PvcMainPageService {

    override fun getFilesList(): List<PvcFileInfoDto> {
        val processorResponse = processorControllerClient.getFilesList()
        val result = if (processorResponse.statusCode.is2xxSuccessful) {
            processorResponse.body
        } else {
            listOf()
        }
        return result
    }

    override fun setFile(pvcFileDto: PvcFileDto): PvcFileInfoDto? {
        val processorResponse = processorControllerClient.setFile(pvcFileDto)
        val result = if (processorResponse.statusCode.is2xxSuccessful) {
            processorResponse.body
        } else {
            null
        }
        return result
    }

    override fun getFile(id: String): PvcFileDto? {
        val processorResponse = processorControllerClient.getFile(id)
        val result = if (processorResponse.statusCode.is2xxSuccessful) {
            processorResponse.body
        } else {
            null
        }
        return result
    }
}