package com.objects.shared.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface DataStorageMainController {

    @GetMapping(path = ["/files/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFile(@PathVariable("id") id: String): PvcFileDto

    @GetMapping(path = ["/files"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFileList(): List<PvcFileInfoDto>

    @PostMapping(
        path = ["/files"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadPvcFile(@Valid @RequestBody pvcFile: PvcFileDto): PvcFileInfoDto
}