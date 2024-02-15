package com.objects.shared.controller

import org.springframework.http.ResponseEntity
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

interface DataStorageMainController {

    @GetMapping(path = ["/files/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFile(id: String): ResponseEntity<PvcFileDto>

    @GetMapping(path = ["/files"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>>

    @PostMapping(
        path = ["/files"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadPvcFile(@Valid pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto>
}