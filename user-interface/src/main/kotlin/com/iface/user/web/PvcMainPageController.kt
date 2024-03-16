package com.iface.user.web

import jakarta.validation.Valid
import com.iface.user.model.PvcIncomeData
import com.iface.user.service.PvcMainPageService
import com.objects.shared.dto.PvcFileDto
import com.objects.shared.dto.PvcFileInfoDto
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.support.SessionStatus

@Controller
class PvcMainPageController(
    private val pvcMainPageService: PvcMainPageService
) {

    @ModelAttribute(name = "pvcIncomeData")
    fun pvcIncomeData(): PvcIncomeData {
        return PvcIncomeData()
    }

    @ModelAttribute(name = "fileList")
    fun listUserFiles(): List<PvcFileInfoDto> {
        return pvcMainPageService.getFilesList()
    }

    @GetMapping
    fun main(): String {
        return "index"
    }

    @GetMapping("/download/{id}")
    fun downloadFile(@PathVariable id: String): ResponseEntity<Resource> {
        val pvcFileDto = pvcMainPageService.getFile(id)
        val resource = object : ByteArrayResource(pvcFileDto.file) {
            override fun getFilename(): String {
                return pvcFileDto.filename
            }
        }
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${resource.filename}\""
            )
            .body(
                resource
            )
    }

    @PostMapping
    fun uploadFile(
        @Valid
        pvcIncomeData: PvcIncomeData,
        errors: Errors,
        sessionStatus: SessionStatus
    ): String {
        if (errors.hasErrors()) {
            return "index"
        }
        val multipartFile = pvcIncomeData.file!!
        val uploadResult = pvcMainPageService.setFile(
            PvcFileDto(multipartFile.originalFilename ?: "filename", multipartFile.bytes)
        )
        sessionStatus.setComplete()
        // TODO Add redirect attribute with new file name
        return "redirect:/"
    }
}