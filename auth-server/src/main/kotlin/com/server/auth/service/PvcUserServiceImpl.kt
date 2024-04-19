package com.server.auth.service

import com.objects.shared.dto.PvcUserDto
import com.server.auth.feign.DataStorageClient
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PvcUserServiceImpl(
    private val dataStorageClient: DataStorageClient
) : PvcUserService {

    override fun authPvcUser(username: String): UserDetails {
        val authData = dataStorageClient.authPvcUser(username)
        val simpleUserDetails = object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf()
            override fun getPassword(): String = "{noop}${authData.password}"
            override fun getUsername(): String = authData.username
            override fun isAccountNonExpired(): Boolean = true
            override fun isAccountNonLocked(): Boolean = true
            override fun isCredentialsNonExpired(): Boolean = true
            override fun isEnabled(): Boolean = true
        }
        return simpleUserDetails
    }

    override fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserDto {
        return dataStorageClient.savePvcUser(pvcUserDto)
    }
}