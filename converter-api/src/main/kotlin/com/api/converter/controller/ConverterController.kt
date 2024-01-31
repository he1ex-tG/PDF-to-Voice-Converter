package com.api.converter.controller

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated

@Validated
interface ConverterController {

    fun convert(
        @NotNull(message = "Input byte array is null")
        @NotEmpty(message = "Input byte array is empty")
        data: ByteArray?
    ): ResponseEntity<ByteArray>

}