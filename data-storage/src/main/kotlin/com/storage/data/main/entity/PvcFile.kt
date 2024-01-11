package com.storage.data.main.entity

import com.shared.objects.dto.PvcFileDto
import com.shared.objects.dto.PvcFileInfoDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Document(collection = "PVC_file")
class PvcFile(
    @Id
    var id: String? = null,
    val filename: String,
    val pvcUserId: String,
    val dateTime: LocalDateTime = OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()
) {

    constructor(pvcFileDto: PvcFileDto, pvcUserId: String) : this(null, pvcFileDto.filename, pvcUserId)

    fun toPvcFileInfoDto(): PvcFileInfoDto = PvcFileInfoDto(id ?: throw NullPointerException(), filename)
    fun toPvcFileDto(file: ByteArray): PvcFileDto = PvcFileDto(filename, file)
}