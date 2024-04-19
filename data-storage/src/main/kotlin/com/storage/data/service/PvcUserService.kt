package com.storage.data.service

import com.objects.shared.dto.PvcUserDto

interface PvcUserService {

    fun authPvcUser(username: String): PvcUserDto

    fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserDto
}