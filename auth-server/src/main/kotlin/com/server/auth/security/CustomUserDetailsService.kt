package com.server.auth.security

import com.server.auth.service.PvcUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val pvcUserService: PvcUserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return pvcUserService.authPvcUser(username)
    }
}