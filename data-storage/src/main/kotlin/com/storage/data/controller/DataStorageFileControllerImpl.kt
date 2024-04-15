package com.storage.data.controller

import com.objects.shared.controller.DataStorageFileController
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.storage.data.service.PvcFileService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class DataStorageFileControllerImpl(
    private val pvcFileService: PvcFileService
) : DataStorageFileController {

    @ResponseStatus(value = HttpStatus.OK)
    override fun downloadPvcFile(pvcFileId: String, pvcUserId: String): PvcFileDto = pvcFileService.loadPvcFile(pvcFileId, pvcUserId)

    @ResponseStatus(value = HttpStatus.OK)
    override fun downloadPvcFileList(pvcUserId: String): List<PvcFileInfoDto> = pvcFileService.getPvcFileList(pvcUserId)

    @ResponseStatus(value = HttpStatus.CREATED)
    override fun uploadPvcFile(pvcFile: PvcFileDto, pvcUserId: String): PvcFileInfoDto = pvcFileService.savePvcFile(pvcFile, pvcUserId)
}