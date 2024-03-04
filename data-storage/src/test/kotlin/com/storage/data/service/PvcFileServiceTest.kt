package com.storage.data.service

import com.mongodb.MongoException
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.configuration.PvcDataStorageConfig
import com.storage.data.entity.PvcFile
import com.storage.data.exception.SavePvcFileException
import com.storage.data.repository.PvcFileRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
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
}

object MockitoHelper {
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}