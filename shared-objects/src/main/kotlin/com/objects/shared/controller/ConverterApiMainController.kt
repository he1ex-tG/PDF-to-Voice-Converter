package com.objects.shared.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping

interface ConverterApiMainController {

    @PostMapping(
        path = ["/converter"],
        consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    fun convert(data: ByteArray): ByteArray
}