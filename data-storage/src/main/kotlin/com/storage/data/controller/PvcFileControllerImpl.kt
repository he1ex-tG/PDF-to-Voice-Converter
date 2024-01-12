package com.storage.data.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.service.PvcFileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/files"], produces = ["application/json"])
class PvcFileControllerImpl(
    private val pvcFileService: PvcFileService
) : PvcFileController {

    @GetMapping(path = ["/{id}"])
    override fun downloadPvcFile(@PathVariable("id") id: String): ResponseEntity<PvcFileDto> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.loadPvcFile(id))
    }

    @GetMapping
    override fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.getPvcFileList())
    }

    @PostMapping
    override fun uploadPvcFile(@RequestBody pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.savePvcFile(pvcFile))
    }
}