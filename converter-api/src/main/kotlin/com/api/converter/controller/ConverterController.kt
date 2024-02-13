package com.api.converter.controller

import org.springframework.http.ResponseEntity

interface ConverterController {

    fun convert(
        data: ByteArray
    ): ResponseEntity<ByteArray>

}