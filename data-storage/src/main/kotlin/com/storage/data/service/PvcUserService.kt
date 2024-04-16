package com.storage.data.service

import com.objects.shared.dto.PvcUserDto

interface PvcUserService {

    fun loadPvcUser(username: String): PvcUserDto

    fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserDto
}