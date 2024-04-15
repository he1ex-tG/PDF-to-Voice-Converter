package com.objects.shared.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface ConverterApiMainController {

    @PostMapping(
        path = ["\${pvc.converterApi.converterEndpoint}"],
        consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    fun convert(@RequestBody data: ByteArray): ByteArray
}