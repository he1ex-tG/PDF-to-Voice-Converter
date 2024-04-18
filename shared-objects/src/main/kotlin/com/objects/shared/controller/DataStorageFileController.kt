package com.objects.shared.controller

import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface DataStorageFileController {

    @GetMapping(
        path = ["\${pvc.dataStorage.usersEndpoint}/{userId}\${pvc.dataStorage.filesEndpoint}/{fileId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun downloadPvcFile(@PathVariable("userId") pvcUserId: String, @PathVariable("fileId") pvcFileId: String): PvcFileDto

    @GetMapping(
        path = ["\${pvc.dataStorage.usersEndpoint}/{userId}\${pvc.dataStorage.filesEndpoint}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun downloadPvcFileList(@PathVariable("userId") pvcUserId: String): List<PvcFileInfoDto>

    @PostMapping(
        path = ["\${pvc.dataStorage.usersEndpoint}/{userId}\${pvc.dataStorage.filesEndpoint}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadPvcFile(@PathVariable("userId") pvcUserId: String, @Valid @RequestBody pvcFile: PvcFileDto): PvcFileInfoDto
}