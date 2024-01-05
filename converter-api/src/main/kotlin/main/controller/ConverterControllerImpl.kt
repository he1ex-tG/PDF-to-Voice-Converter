package main.controller

import main.service.ConverterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/converter"], produces = ["application/json"])
class ConverterControllerImpl(
    val converterService: ConverterService
) : ConverterController {

    @PostMapping
    override fun convert(@RequestBody data: ByteArray): ResponseEntity<ByteArray> {
        return ResponseEntity
            .ok()
            .body(converterService.convert(data))
    }
}