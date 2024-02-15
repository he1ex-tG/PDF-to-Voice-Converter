package com.api.converter.controller

import com.api.converter.service.ConverterService
import com.objects.shared.controller.ConverterApiMainController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1"])
class ConverterApiMainControllerImpl(
    val converterService: ConverterService
) : ConverterApiMainController {

    override fun convert(@RequestBody data: ByteArray): ResponseEntity<ByteArray> {
        return ResponseEntity
            .ok()
            .body(converterService.convert(data))
    }
}