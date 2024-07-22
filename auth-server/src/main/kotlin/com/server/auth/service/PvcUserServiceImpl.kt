package com.server.auth.service

import com.objects.shared.dto.PvcUserDto
import com.objects.shared.dto.PvcUserInfoDto
import com.server.auth.feign.DataStorageClient
import com.server.auth.security.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PvcUserServiceImpl(
    private val dataStorageClient: DataStorageClient
) : PvcUserService {

    override fun authPvcUser(username: String): UserDetails {
        val authData = dataStorageClient.authPvcUser(username)
        return CustomUserDetails(authData)
    }

    override fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserInfoDto {
        return dataStorageClient.savePvcUser(pvcUserDto)
    }
}