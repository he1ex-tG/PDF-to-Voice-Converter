package com.storage.data.service

import com.objects.shared.dto.PvcUserDto
import com.objects.shared.dto.PvcUserInfoDto

interface PvcUserService {

    fun authPvcUser(username: String): PvcUserInfoDto

    fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserInfoDto
}