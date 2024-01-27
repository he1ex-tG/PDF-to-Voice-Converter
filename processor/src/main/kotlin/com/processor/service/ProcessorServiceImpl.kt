package com.processor.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.processor.feign.ConverterApiClient
import com.processor.feign.DataStorageClient
import org.springframework.stereotype.Service

@Service
class ProcessorServiceImpl(
    private val converterApiClient: ConverterApiClient,
    private val dataStorageClient: DataStorageClient
) : ProcessorService {

    override fun getFilesList(): List<PvcFileInfoDto> {
        return dataStorageClient.getPvcFilesList()
    }

    override fun convertAndStoreFile(pvcFileDto: PvcFileDto): PvcFileInfoDto {
        val convertedPdf = converterApiClient.convert(pvcFileDto.file)
        val newFilename = (pvcFileDto.filename.substringBeforeLast('.')) + ".mp3"
        return dataStorageClient.sendPvcFile(PvcFileDto(newFilename, convertedPdf))
    }

    override fun getFile(id: String): PvcFileDto {
        return dataStorageClient.getPvcFile(id)
    }
}