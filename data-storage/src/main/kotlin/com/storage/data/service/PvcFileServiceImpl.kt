package com.storage.data.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.configuration.PvcFileServiceConfig
import com.storage.data.entity.PvcFile
import com.storage.data.entity.PvcUser
import com.storage.data.exception.LoadPvcFileException
import com.storage.data.exception.SavePvcFileException
import com.storage.data.repository.PvcFileRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.FileOutputStream

@Service
@EnableConfigurationProperties(PvcFileServiceConfig::class)
class PvcFileServiceImpl(
    private val pvcFileRepository: PvcFileRepository,
    private val pvcFileServiceConfig: PvcFileServiceConfig
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
        val pvcFile = pvcFileRepository.save(PvcFile(pvcFileDto, pvcUserTemplate.id!!))
        if (pvcFile.id == null) {
            throw SavePvcFileException("Save file to repository function thrown an exception, file not save")
        }
        try {
            FileOutputStream(filePathBuilder(pvcFile.id!!)).write(pvcFileDto.file)
        }
        catch (_: Throwable) {
            throw SavePvcFileException("Save file to repository function thrown an exception, file not save")
        }
        return pvcFile.toPvcFileInfoDto()
    }

    override fun loadPvcFile(pvcFileId: String): PvcFileDto {
        val pvcFile = pvcFileRepository.findByIdAndPvcUserId(pvcFileId, pvcUserTemplate.id!!)
            .orElseThrow {
                LoadPvcFileException("Load file from repository function thrown an exception, file with id = ${pvcFileId} not load")
            }
        try {
            val fileByteArray = FileInputStream(filePathBuilder(pvcFile.id!!)).readBytes()
            return pvcFile.toPvcFileDto(fileByteArray)
        } catch (_: Throwable) {
            throw LoadPvcFileException("Load file from repository function thrown an exception, file with id = ${pvcFileId} not load")
        }
    }

    override fun getPvcFileList(): List<PvcFileInfoDto> {
        return pvcFileRepository.findAllByPvcUserId(pvcUserTemplate.id!!)
            .map {
                it.toPvcFileInfoDto()
            }
    }
}