package com.processor.controller

import com.objects.shared.controller.ProcessorMainController
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import com.processor.service.ProcessorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class ProcessorMainControllerImpl(
    private val processorService: ProcessorService
) : ProcessorMainController {

    @ResponseStatus(value = HttpStatus.OK)
    override fun getFile(id: String): PvcFileDto = processorService.getFile(id)

    @ResponseStatus(value = HttpStatus.OK)
    override fun getFilesList(): List<PvcFileInfoDto> = processorService.getFilesList()

    @ResponseStatus(value = HttpStatus.CREATED)
    override fun addFile(pvcFileDto: PvcFileDto): PvcFileInfoDto = processorService.convertAndStoreFile(pvcFileDto)
}