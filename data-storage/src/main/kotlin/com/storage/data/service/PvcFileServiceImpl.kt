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
        try {
            val pvcFile = pvcFileRepository.save(PvcFile(pvcFileDto, pvcUserTemplate.id!!))
            val fileOutputStream = FileOutputStream(filePathBuilder(pvcFile.id!!))
            fileOutputStream.write(pvcFileDto.file)
            fileOutputStream.close()
            return pvcFile.toPvcFileInfoDto()
        }
        catch (_: Throwable) {
            throw SavePvcFileException("Save file to repository function thrown an exception, file not save")
        }
    }

    override fun loadPvcFile(pvcFileId: String): PvcFileDto {
        try {
            val pvcFileOptional = pvcFileRepository.findByIdAndPvcUserId(pvcFileId, pvcUserTemplate.id!!)
            val pvcFile = pvcFileOptional.get()
            val fileInputStream = FileInputStream(filePathBuilder(pvcFile.id!!))
            val fileByteArray = fileInputStream.readBytes()
            fileInputStream.close()
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