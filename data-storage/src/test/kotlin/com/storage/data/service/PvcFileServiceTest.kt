package com.storage.data.service

import com.mongodb.MongoException
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.configuration.PvcDataStorageConfig
import com.storage.data.entity.PvcFile
import com.storage.data.exception.LoadPvcFileException
import com.storage.data.exception.SavePvcFileException
import com.storage.data.repository.PvcFileRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class PvcFileServiceTest {

    @Mock
    lateinit var pvcFileRepository: PvcFileRepository
    @Mock
    lateinit var pvcDataStorageConfig: PvcDataStorageConfig

    private lateinit var pvcFileService: PvcFileService

    @BeforeEach
    fun pvcFileServiceInit() {
        pvcFileService = PvcFileServiceImpl(pvcFileRepository, pvcDataStorageConfig)
    }

    private val pvcFileId = UUID.randomUUID().toString()
    private val pvcUserId = UUID.randomUUID().toString()
    private val filename = UUID.randomUUID().toString()
    private val fileByteArray = byteArrayOf(1, 2, 3)
    private val nowDate = OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
    private val pvcFileDto = PvcFileDto(filename, fileByteArray)
    private val pvcFileInfoDto = PvcFileInfoDto(pvcFileId, filename)
    private val pvcFileNullId = PvcFile(null, filename, pvcUserId, nowDate)
    private val pvcFile = PvcFile(pvcFileId, filename, pvcUserId, nowDate)

    @Test
    fun `savePvcFile gets MongoDBException`() {
        given(pvcFileRepository.save(MockitoHelper.anyObject())).willAnswer {
            throw MongoException("test exception")
        }
        assertThrows<MongoException>("test exception") {
            pvcFileService.savePvcFile(pvcFileDto, pvcUserId)
        }
    }

    @Test
    fun `savePvcFile - MongoDB returns object with null id - throw SavePvcFileException`() {
        given(pvcFileRepository.save(MockitoHelper.anyObject())).willReturn(pvcFileNullId)
        assertThrows<SavePvcFileException> {
            pvcFileService.savePvcFile(pvcFileDto, pvcUserId)
        }
    }

    @Test
    fun `savePvcFile - MongoDB returns normal object and no errors while saving`() {
        given(pvcFileRepository.save(MockitoHelper.anyObject())).willReturn(pvcFile)
        // Real place on the disk. File is created here.
        given(pvcDataStorageConfig.localStoragePath).willReturn("E:\\PDF_to_Voice_Converter_storage")
        given(pvcDataStorageConfig.localStorageSize).willReturn("2")
        val result = pvcFileService.savePvcFile(pvcFileDto, pvcUserId)
        assertThat(result.id).isEqualTo(pvcFile.id)
        assertThat(result.filename).isEqualTo(pvcFile.filename)
        // File is deleted here.
        pvcFileService.deletePvcFile(pvcFile.id!!, pvcUserId)
    }

    @Test
    fun `loadPvcFile gets MongoDBException`() {
        given(pvcFileRepository.findByIdAndPvcUserId(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            throw MongoException("test exception")
        }
        assertThrows<MongoException>("test exception") {
            pvcFileService.loadPvcFile(pvcFileId, pvcUserId)
        }
    }

    @Test
    fun `loadPvcFile - MongoDB returns object with null id - throw LoadPvcFileException`() {
        given(pvcFileRepository.findByIdAndPvcUserId(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            Optional.ofNullable(null)
        }
        assertThrows<LoadPvcFileException> {
            pvcFileService.loadPvcFile(pvcFileId, pvcUserId)
        }
    }

    @Test
    fun `loadPvcFile - file not found - throw error`() {
        given(pvcFileRepository.findByIdAndPvcUserId(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            Optional.of(pvcFile)
        }
        given(pvcDataStorageConfig.localStoragePath).willReturn("E:\\PDF_to_Voice_Converter_storage")
        assertThrows<LoadPvcFileException> {
            pvcFileService.loadPvcFile(pvcFileId, pvcUserId)
        }
    }

    @Test
    fun `loadPvcFile - returns ok`() {
        given(pvcFileRepository.findByIdAndPvcUserId(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            Optional.of(pvcFile)
        }
        given(pvcFileRepository.save(MockitoHelper.anyObject())).willReturn(pvcFile)
        given(pvcDataStorageConfig.localStoragePath).willReturn("E:\\PDF_to_Voice_Converter_storage")
        given(pvcDataStorageConfig.localStorageSize).willReturn("2")

        val saveResult = pvcFileService.savePvcFile(pvcFileDto, pvcUserId)
        val loadResult = pvcFileService.loadPvcFile(saveResult.id, pvcUserId)
        assertThat(loadResult.filename).isEqualTo(filename)
        assertThat(loadResult.file).contains(1, 2, 3)

        pvcFileService.deletePvcFile(pvcFileId, pvcUserId)
    }

    @Test
    fun `getPvcFileList gets MongoDBException`() {
        given(pvcFileRepository.findAllByPvcUserId(MockitoHelper.anyObject())).willAnswer {
            throw MongoException("test exception")
        }
        assertThrows<MongoException>("test exception") {
            pvcFileService.getPvcFileList(pvcUserId)
        }
    }

    @Test
    fun `getPvcFileList gets empty`() {
        given(pvcFileRepository.findAllByPvcUserId(MockitoHelper.anyObject())).willAnswer {
            listOf<PvcFileInfoDto>()
        }
        val result = pvcFileService.getPvcFileList(pvcUserId)
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `getPvcFileList gets not empty`() {
        given(pvcFileRepository.findAllByPvcUserId(MockitoHelper.anyObject())).willAnswer {
            listOf(pvcFile)
        }
        val result = pvcFileService.getPvcFileList(pvcUserId)
        assertThat(result.size).isEqualTo(1)
        assertThat(result).contains(pvcFileInfoDto)
    }
}

object MockitoHelper {
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}