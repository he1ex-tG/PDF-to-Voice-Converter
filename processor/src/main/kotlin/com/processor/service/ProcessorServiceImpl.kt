package com.processor.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.processor.feign.ConverterApiClient
import com.processor.feign.DataStorageClient
import com.processor.security.CurrentPvcUser
import org.springframework.stereotype.Service

@Service
class ProcessorServiceImpl(
    private val converterApiClient: ConverterApiClient,
    private val dataStorageClient: DataStorageClient
) : ProcessorService {

    override fun getFilesList(): List<PvcFileInfoDto> {
        return dataStorageClient.downloadPvcFileList(CurrentPvcUser.id)
    }

    override fun convertAndStoreFile(pvcFileDto: PvcFileDto): PvcFileInfoDto {
        val convertedPdf = converterApiClient.convert(pvcFileDto.file)
        val newFilename = (pvcFileDto.filename.substringBeforeLast('.')) + ".mp3"
        return dataStorageClient.uploadPvcFile(CurrentPvcUser.id, PvcFileDto(newFilename, convertedPdf))
    }

    override fun getFile(fileId: String): PvcFileDto {
        return dataStorageClient.downloadPvcFile(CurrentPvcUser.id, fileId)
    }
}