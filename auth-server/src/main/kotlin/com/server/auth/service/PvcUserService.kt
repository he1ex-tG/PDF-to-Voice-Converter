package com.server.auth.service

import com.objects.shared.dto.PvcUserDto
import org.springframework.security.core.userdetails.UserDetails

interface PvcUserService {

    fun authPvcUser(username: String): UserDetails

    fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserDto
}