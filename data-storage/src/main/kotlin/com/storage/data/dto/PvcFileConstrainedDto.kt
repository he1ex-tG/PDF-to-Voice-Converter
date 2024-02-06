package com.storage.data.dto

import com.objects.shared.dto.PvcFileDto
import jakarta.validation.constraints.NotEmpty

class PvcFileConstrainedDto(
    @field:NotEmpty(message = "{dataStorage.pvcFileConstrainedDto.filename.isEmpty}")
    override val filename: String,
    @field:NotEmpty(message = "{dataStorage.pvcFileConstrainedDto.file.isEmpty}")
    override val file: ByteArray
) : PvcFileDto(filename, file)