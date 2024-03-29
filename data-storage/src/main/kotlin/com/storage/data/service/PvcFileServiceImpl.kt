package com.storage.data.service

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.configuration.PvcDataStorageConfig
import com.storage.data.entity.PvcFile
import com.storage.data.exception.LoadPvcFileException
import com.storage.data.exception.SavePvcFileException
import com.storage.data.repository.PvcFileRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

@Service
@EnableConfigurationProperties(PvcDataStorageConfig::class)
class PvcFileServiceImpl(
    private val pvcFileRepository: PvcFileRepository,
    private val pvcDataStorageConfig: PvcDataStorageConfig,
) : PvcFileService {

    private fun filePathBuilder(filename: String): Path {
        val localStoragePath = Path(pvcDataStorageConfig.localStoragePath, filename)
        if (!Files.isDirectory(localStoragePath.parent)) {
            Files.createDirectories(localStoragePath.parent)
        }
        return localStoragePath
    }

    private fun deleteOverageFiles(pvcUserId: String) {
        val localStorageSize = pvcDataStorageConfig.localStorageSize.toInt()
        val userFiles = pvcFileRepository.findAllByPvcUserId(pvcUserId).sortedByDescending(PvcFile::dateTime)
        for (index in localStorageSize until userFiles.count()) {
            deletePvcFile(userFiles.elementAt(index).id!!, pvcUserId)
        }
    }

    override fun savePvcFile(pvcFileDto: PvcFileDto, pvcUserId: String): PvcFileInfoDto {
        val pvcFile = pvcFileRepository.save(PvcFile(pvcFileDto, pvcUserId))
        try {
            val fileOutputStream = FileOutputStream(filePathBuilder(pvcFile.id!!).pathString)
            fileOutputStream.write(pvcFileDto.file)
            fileOutputStream.close()
            // Delete overage files
            deleteOverageFiles(pvcUserId)
            return pvcFile.toPvcFileInfoDto()
        }
        catch (ex: Throwable) {
            throw SavePvcFileException("Save file to repository function thrown an exception with message: " +
                    "${ex.message ?: "No message"}, file is not saved")
        }
    }

    override fun loadPvcFile(pvcFileId: String, pvcUserId: String): PvcFileDto {
        val pvcFileOptional = pvcFileRepository.findByIdAndPvcUserId(pvcFileId, pvcUserId)
        try {
            val pvcFile = pvcFileOptional.get()
            val fileInputStream = FileInputStream(filePathBuilder(pvcFile.id!!).pathString)
            val fileByteArray = fileInputStream.readBytes()
            fileInputStream.close()
            return pvcFile.toPvcFileDto(fileByteArray)
        } catch (ex: Throwable) {
            throw LoadPvcFileException("Load file from repository function thrown an exception with message: " +
                    "${ex.message ?: "No message"}, file with id = $pvcFileId is not loaded")
        }
    }

    override fun getPvcFileList(pvcUserId: String): List<PvcFileInfoDto> {
        return pvcFileRepository.findAllByPvcUserId(pvcUserId)
            .map {
                it.toPvcFileInfoDto()
            }
    }

    override fun deletePvcFile(pvcFileId: String, pvcUserId: String) {
        try {
            Files.deleteIfExists(filePathBuilder(pvcFileId))
        }
        finally {
            pvcFileRepository.deleteByIdAndPvcUserId(pvcFileId, pvcUserId)
        }
    }
}