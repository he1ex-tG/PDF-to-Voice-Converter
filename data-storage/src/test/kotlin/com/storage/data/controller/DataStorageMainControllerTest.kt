package com.storage.data.controller

import com.mongodb.MongoException
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.exception.LoadPvcFileException
import com.storage.data.service.PvcFileService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID

@WebMvcTest(controllers = [DataStorageMainControllerImpl::class])
class ConverterApiMainControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var pvcFileService: PvcFileService

    @Test
    fun `downloadPvcFile gets null in path - 404`() {
        mockMvc.get("/api/v1/files/") {
            content = null
        }.andExpect {
            status {
                isNotFound()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Not Found")
                }
            }
        }
    }

    @Test
    fun `downloadPvcFile gets id but database error - throw mongo base exception`() {
        given(pvcFileService.loadPvcFile(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            throw MongoException("Some exception")
        }
        mockMvc.get("/api/v1/files/fileRandomId") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isInternalServerError()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Internal Server Error")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Database exception"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFile gets id but file not read or absent - throw LoadPvcFileException exception`() {
        given(pvcFileService.loadPvcFile(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            throw LoadPvcFileException()
        }
        mockMvc.get("/api/v1/files/fileRandomId") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isInternalServerError()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Internal Server Error")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Load file function thrown an exception, file not load"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFile gets id but file is not belongs to the user - throw LoadPvcFileException exception`() {
        given(pvcFileService.loadPvcFile(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            throw LoadPvcFileException()
        }
        mockMvc.get("/api/v1/files/fileRandomId") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isInternalServerError()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Internal Server Error")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Load file function thrown an exception, file not load"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFile gets file id but missing param pvcUserId - bad request`() {
        mockMvc.get("/api/v1/files/fileRandomId") {
            content = null
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("pvcUserId"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFile gets id and ends with normal - 200`() {
        given(pvcFileService.loadPvcFile(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            PvcFileDto("name", byteArrayOf(1, 2, 3))
        }
        mockMvc.get("/api/v1/files/fileRandomId") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isOk()
            }
            content {
                jsonPath("filename") {
                    value("name")
                }
                jsonPath("file") {
                    isNotEmpty()
                }
            }
        }
    }

    @Test
    fun `downloadPvcFileList executed but database error - throw mongo base exception`() {
        given(pvcFileService.getPvcFileList(MockitoHelper.anyObject())).willAnswer {
            throw MongoException("Some exception")
        }
        mockMvc.get("/api/v1/files") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isInternalServerError()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Internal Server Error")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Database exception"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFileList executed with missing param pvcUserId - bad request`() {
        mockMvc.get("/api/v1/files") {
            content = null
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("pvcUserId"))
                }
            }
        }
    }

    @Test
    fun `downloadPvcFileList executed and ends with normal - 200`() {
        given(pvcFileService.getPvcFileList(MockitoHelper.anyObject())).willAnswer {
            listOf(
                PvcFileInfoDto("id1", "filename1"),
                PvcFileInfoDto("id2", "filename2")
            )
        }
        mockMvc.get("/api/v1/files") {
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isOk()
            }
            content {
                jsonPath("$.[0].id") {
                    value("id1")
                }
                jsonPath("$.[0].filename") {
                    value("filename1")
                }
                jsonPath("$.[1].id") {
                    value("id2")
                }
                jsonPath("$.[1].filename") {
                    value("filename2")
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile gets null request body - throw HttpMessageNotReadableException`() {
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            param("pvcUserId", "pvcUserId")
            content = null
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Required request body is missing"))
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile gets wrong object in body - throw JSON parsing error HttpMessageNotReadableException`() {
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            param("pvcUserId", "pvcUserId")
            content = "{\"filename\":\"filename\", \"not_file_param\":[0, 1, 2]}"
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("JSON parse error"))
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile gets expected object but field is null - throw JSON parsing error HttpMessageNotReadableException`() {
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            param("pvcUserId", "pvcUserId")
            content = "{\"filename\":null, \"file\":[0, 1, 2]}"
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("JSON parse error"))
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile gets expected object but field is empty - throw validation error MethodArgumentNotValidException`() {
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            param("pvcUserId", "pvcUserId")
            content = "{\"filename\":\"\", \"file\":[0, 1, 2]}"
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("Validation failed"))
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile executed with missing param pvcUserId - bad request`() {
        val pvcFileInfoDto = PvcFileInfoDto(UUID.randomUUID().toString(), "filename")
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            content = "{\"filename\":\"${pvcFileInfoDto.filename}\", \"file\":[0, 1, 2]}"
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                jsonPath("type") {
                    value("about:blank")
                }
                jsonPath("title") {
                    value("Bad Request")
                }
                jsonPath("detail") {
                    value(Matchers.containsString("pvcUserId"))
                }
            }
        }
    }

    @Test
    fun `uploadPvcFile executed and ends with normal - 201`() {
        val pvcFileInfoDto = PvcFileInfoDto(UUID.randomUUID().toString(), "filename")
        given(pvcFileService.savePvcFile(MockitoHelper.anyObject(), MockitoHelper.anyObject())).willAnswer {
            pvcFileInfoDto
        }
        mockMvc.post("/api/v1/files") {
            headers {
                set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            param("pvcUserId", "pvcUserId")
            content = "{\"filename\":\"${pvcFileInfoDto.filename}\", \"file\":[0, 1, 2]}"
        }.andExpect {
            status {
                isCreated()
            }
            content {
                jsonPath("id") {
                    value(pvcFileInfoDto.id)
                }
                jsonPath("filename") {
                    value(pvcFileInfoDto.filename)
                }
            }
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