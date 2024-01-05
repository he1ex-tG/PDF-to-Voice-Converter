package main.controller

import org.springframework.http.ResponseEntity

interface ConverterController {

    fun convert(data: ByteArray): ResponseEntity<ByteArray>
}