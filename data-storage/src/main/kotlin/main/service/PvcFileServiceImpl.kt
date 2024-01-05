package main.service

import main.configuration.PvcFileServiceConfig
import main.dto.PvcFileDto
import main.dto.PvcFileInfoDto
import main.entity.PvcFile
import main.entity.PvcUser
import main.repository.PvcFileRepository
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