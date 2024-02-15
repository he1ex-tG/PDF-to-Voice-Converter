package com.objects.shared.dto

import jakarta.validation.constraints.NotEmpty

open class PvcFileDto(
    @field:NotEmpty(message = "{dataStorage.pvcFileConstrainedDto.filename.isEmpty}")
    open val filename: String,
    @field:NotEmpty(message = "{dataStorage.pvcFileConstrainedDto.file.isEmpty}")
    open val file: ByteArray
)