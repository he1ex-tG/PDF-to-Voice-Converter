package com.storage.data.main.service

import com.shared.objects.dto.PvcFileDto
import com.shared.objects.dto.PvcFileInfoDto
import com.storage.data.main.configuration.PvcFileServiceConfig
import com.storage.data.main.entity.PvcFile
import com.storage.data.main.entity.PvcUser
import com.storage.data.main.repository.PvcFileRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException

@Service
@EnableConfigurationProperties(PvcFileServiceConfig::class)
class PvcFileServiceImpl(
    val pvcFileRepository: PvcFileRepository,
    val pvcFileServiceConfig: PvcFileServiceConfig
) : PvcFileService {

    private val pvcUserTemplate = PvcUser("id12345", "templateUser", "templatePassword")

    private fun filePathBuilder(filename: String): String {
        val localStoragePath = pvcFileServiceConfig.localStoragePath
        return if (localStoragePath.last() != '\\') {
            localStoragePath + "\\" + filename
        } else {
            localStoragePath + filename
        }
    }

    override fun savePvcFile(pvcFileDto: PvcFileDto): PvcFileInfoDto {
        val fileByteArray: ByteArray = pvcFileDto.file
        val pvcFile = pvcFileRepository.save(PvcFile(pvcFileDto, pvcUserTemplate.id ?: throw NullPointerException()))
        if (pvcFile.id == null) {
            throw FileNotFoundException()
        }
        File(filePathBuilder(pvcFile.id ?: throw NullPointerException())).writeBytes(fileByteArray)
        return pvcFile.toPvcFileInfoDto()
    }

    override fun loadPvcFile(pvcFileId: String): PvcFileDto {
        val pvcFile = pvcFileRepository.findByIdAndPvcUserId(pvcFileId, pvcUserTemplate.id ?: throw NullPointerException())
            .orElseThrow {
                FileNotFoundException()
            }
        val fileByteArray = File(filePathBuilder(pvcFile.id ?: throw NullPointerException())).readBytes()
        return pvcFile.toPvcFileDto(fileByteArray)
    }

    override fun getPvcFileList(): List<PvcFileInfoDto> {
        return pvcFileRepository.findAllByPvcUserId(pvcUserTemplate.id ?: throw NullPointerException())
            .map {
                it.toPvcFileInfoDto()
            }
    }
}