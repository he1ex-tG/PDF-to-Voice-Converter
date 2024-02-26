package com.storage.data.entity

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Document(collection = "PVC_file")
class PvcFile(
    @Id
    var id: String? = null,
    val filename: String,
    val pvcUserId: String,
    val dateTime: LocalDateTime = OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
) {

    constructor(pvcFileDto: PvcFileDto, pvcUserId: String) : this(null, pvcFileDto.filename, pvcUserId)

    fun toPvcFileInfoDto(): PvcFileInfoDto = PvcFileInfoDto(id ?: throw NullPointerException(), filename)
    fun toPvcFileDto(file: ByteArray): PvcFileDto = PvcFileDto(filename, file)

    override fun toString(): String {
        return "PvcFile instance: id = ${id ?: "null"}, filename = $filename, " +
                "pvcUserId = $pvcUserId, dateTime = $dateTime"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PvcFile) return false
        if ((id == other.id) && (filename == other.filename) && (pvcUserId == other.pvcUserId) && (dateTime == other.dateTime)) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * filename.hashCode() + 31 * pvcUserId.hashCode() + 31 * dateTime.hashCode()
    }
}