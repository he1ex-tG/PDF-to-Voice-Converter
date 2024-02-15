package com.storage.data.controller

import com.objects.shared.controller.DataStorageMainController
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.service.PvcFileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class DataStorageMainControllerImpl(
    private val pvcFileService: PvcFileService
) : DataStorageMainController {

    override fun downloadPvcFile(@PathVariable("id") id: String): ResponseEntity<PvcFileDto> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.loadPvcFile(id))
    }

    override fun downloadPvcFileList(): ResponseEntity<List<PvcFileInfoDto>> {
        return ResponseEntity
            .ok()
            .body(pvcFileService.getPvcFileList())
    }

    override fun uploadPvcFile(@RequestBody pvcFile: PvcFileDto): ResponseEntity<PvcFileInfoDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(pvcFileService.savePvcFile(pvcFile))
    }
}