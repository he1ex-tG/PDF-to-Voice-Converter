package com.storage.data.controller

import com.objects.shared.dto.PvcFileDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PvcFileControllerTest {

    @Autowired
    lateinit var pvcFileController: PvcFileController

    val pvcFileDto = PvcFileDto("test_file_1.txt", byteArrayOf(100, 100, 100, 100, 100, 5))

    @Test
    @Order(1)
    fun `downloadPvcFileList return empty list`() {
        val response = pvcFileController.downloadPvcFileList()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.isEmpty()).isTrue()
    }

    @Test
    @Order(2)
    fun `uploadPvcFile return ok`() {
        val response = pvcFileController.uploadPvcFile(pvcFileDto)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.id).isNotEmpty()
        assertThat(response.body!!.filename).isEqualTo(pvcFileDto.filename)
    }

    @Test
    @Order(3)
    fun `downloadPvcFileList return list of 1`() {
        val response = pvcFileController.downloadPvcFileList()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.size).isEqualTo(1)
    }

    @Test
    @Order(4)
    fun `downloadPvcFile return ok`() {
        val file = pvcFileController.downloadPvcFileList().body!![0]
        val response = pvcFileController.downloadPvcFile(file.id)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.file).isEqualTo(pvcFileDto.file)
        assertThat(response.body!!.filename).isEqualTo(pvcFileDto.filename)
    }

}