package com.api.converter.controller

import com.api.converter.service.ConverterService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/converter"])
class ConverterControllerImpl(
    val converterService: ConverterService
) : ConverterController {

    @PostMapping(
        consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    override fun convert(@RequestBody data: ByteArray): ResponseEntity<ByteArray> {
        return ResponseEntity
            .ok()
            .body(converterService.convert(data))
    }
}