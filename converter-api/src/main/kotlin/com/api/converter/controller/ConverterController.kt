package com.api.converter.controller

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated

@Validated
interface ConverterController {

    fun convert(
        @NotNull(message = "{converterApi.convert.data.isNull}")
        @NotEmpty(message = "{converterApi.convert.data.isEmpty}")
        data: ByteArray?
    ): ResponseEntity<ByteArray>

}