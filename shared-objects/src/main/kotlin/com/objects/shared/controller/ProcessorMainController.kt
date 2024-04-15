package com.objects.shared.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface ProcessorMainController {

    @GetMapping(path = ["\${pvc.processor.filesEndpoint}/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFile(@PathVariable("id") id: String): PvcFileDto

    @GetMapping(path = ["\${pvc.processor.filesEndpoint}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFilesList(): List<PvcFileInfoDto>

    @PostMapping(
        path = ["\${pvc.processor.filesEndpoint}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addFile(@Valid @RequestBody pvcFileDto: PvcFileDto): PvcFileInfoDto
}