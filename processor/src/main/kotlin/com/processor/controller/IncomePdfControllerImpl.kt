package com.processor.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.processor.service.MainProcessor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/files"], produces = ["application/json"])
class IncomePdfControllerImpl(
    private val mainProcessor: MainProcessor
) : IncomePdfController {

    @GetMapping
    override fun getFilesList(): ResponseEntity<List<PvcFileInfoDto>> {
        return ResponseEntity
            .ok()
            .body(mainProcessor.getFilesList())
    }

    @PostMapping
    override fun addFile(@RequestBody pvcFileDto: PvcFileDto) {
        mainProcessor.convertAndStoreFile(pvcFileDto)
    }

    @GetMapping("/{id}")
    override fun getFile(@PathVariable("id") id: String): ResponseEntity<PvcFileDto> {
        return ResponseEntity
            .ok()
            .body(mainProcessor.getFile(id))
    }
}