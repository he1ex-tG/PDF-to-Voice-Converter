package com.api.converter.controller

import com.api.converter.service.ConverterService
import com.objects.shared.controller.ConverterApiMainController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1"])
class ConverterApiMainControllerImpl(
    val converterService: ConverterService
) : ConverterApiMainController {

    @ResponseStatus(value = HttpStatus.OK)
    override fun convert(data: ByteArray): ByteArray = converterService.convert(data)
}