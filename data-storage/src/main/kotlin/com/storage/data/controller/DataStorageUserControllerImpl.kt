package com.storage.data.controller

import com.objects.shared.controller.DataStorageUserController
import com.objects.shared.dto.PvcUserDto
import com.objects.shared.dto.PvcUserInfoDto
import com.storage.data.service.PvcUserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1"])
class DataStorageUserControllerImpl(
    private val pvcUserService: PvcUserService
) : DataStorageUserController {

    @ResponseStatus(value = HttpStatus.OK)
    override fun authPvcUser(username: String): PvcUserInfoDto = pvcUserService.authPvcUser(username)

    @ResponseStatus(value = HttpStatus.CREATED)
    override fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserInfoDto = pvcUserService.savePvcUser(pvcUserDto)


}