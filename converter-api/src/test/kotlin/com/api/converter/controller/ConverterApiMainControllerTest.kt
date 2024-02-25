package com.api.converter.controller

import com.api.converter.exception.TtsEmptyStringException
import com.api.converter.service.ConverterService
import com.itextpdf.text.exceptions.InvalidPdfException
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.nio.charset.Charset

@WebMvcTest(controllers = [ConverterApiMainControllerImpl::class])
class ConverterApiMainControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var converterService: ConverterService

    @Test
    fun `convert gets null in request body - throw exception`() {
        mockMvc.post("/api/v1/converter") {
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
    fun `convert gets empty in request body - throw exception`() {
        mockMvc.post("/api/v1/converter") {
            content = byteArrayOf()
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
    fun `convert gets not empty and bad request body - throw exception`() {
        given(converterService.convert(MockitoHelper.anyObject()))
            .willAnswer {
                throw InvalidPdfException("InvalidPdfException.")
            }
        mockMvc.post("/api/v1/converter") {
            content = "Not empty byte array with random data".toByteArray(Charset.defaultCharset())
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
                    value(Matchers.containsString("InvalidPdfException"))
                }
            }
        }
    }

    @Test
    fun `convert gets empty pdf request body - throw exception`() {
        given(converterService.convert(MockitoHelper.anyObject()))
            .willAnswer {
                throw TtsEmptyStringException()
            }
        mockMvc.post("/api/v1/converter") {
            content = "Empty pdf file".toByteArray(Charset.defaultCharset())
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
                    value(Matchers.containsString("Text is empty or consists solely of whitespace characters"))
                }
            }
        }
    }

    @Test
    fun `convert gets not empty pdf request body - OK`() {
        given(converterService.convert(MockitoHelper.anyObject()))
            .willAnswer {
                "Result is ok - mp3 file with voice".toByteArray(Charset.defaultCharset())
            }
        mockMvc.post("/api/v1/converter") {
            content = "Empty pdf file".toByteArray(Charset.defaultCharset())
        }.andExpect {
            status {
                isOk()
            }
            content {
                bytes("Result is ok - mp3 file with voice".toByteArray(Charset.defaultCharset()))
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