package com.api.converter.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated

@Validated
interface ConverterController {

    fun convert(
        data: ByteArray
    ): ResponseEntity<ByteArray>

}