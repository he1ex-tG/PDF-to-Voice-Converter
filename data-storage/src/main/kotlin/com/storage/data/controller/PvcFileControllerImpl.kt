package com.storage.data.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.dto.PvcFileConstrainedDto
import com.storage.data.service.PvcFileService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/files"])
class PvcFileControllerImpl(
    private val pvcFileService: PvcFileService
) : PvcFileController {

    @GetMapping(path = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun downloadPvcFile(@PathVariable("id") id: String): ResponseEntity<PvcFileDto> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.loadPvcFile(id))
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.getPvcFileList())
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    override fun uploadPvcFile(
        @Valid
        @RequestBody
        pvcFile: PvcFileConstrainedDto
    ): ResponseEntity<PvcFileInfoDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(pvcFileService.savePvcFile(pvcFile))
    }
}