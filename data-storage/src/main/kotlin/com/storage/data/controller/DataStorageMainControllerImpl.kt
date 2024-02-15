package com.storage.data.controller

import com.objects.shared.controller.DataStorageMainController
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.service.PvcFileService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class DataStorageMainControllerImpl(
    private val pvcFileService: PvcFileService
) : DataStorageMainController {

    @ResponseStatus(value = HttpStatus.OK)
    override fun downloadPvcFile(@PathVariable("id") id: String): PvcFileDto = pvcFileService.loadPvcFile(id)

    @ResponseStatus(value = HttpStatus.OK)
    override fun downloadPvcFileList(): List<PvcFileInfoDto> = pvcFileService.getPvcFileList()

    @ResponseStatus(value = HttpStatus.CREATED)
    override fun uploadPvcFile(@RequestBody pvcFile: PvcFileDto): PvcFileInfoDto = pvcFileService.savePvcFile(pvcFile)
}