package com.objects.shared.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface DataStorageMainController {

    @GetMapping(path = ["\${pvc.dataStorage.filesEndpoint}/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFile(@PathVariable("id") pvcFileId: String, @RequestParam pvcUserId: String): PvcFileDto

    @GetMapping(path = ["\${pvc.dataStorage.filesEndpoint}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadPvcFileList(@RequestParam pvcUserId: String): List<PvcFileInfoDto>

    @PostMapping(
        path = ["\${pvc.dataStorage.filesEndpoint}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadPvcFile(@Valid @RequestBody pvcFile: PvcFileDto, @RequestParam pvcUserId: String): PvcFileInfoDto
}