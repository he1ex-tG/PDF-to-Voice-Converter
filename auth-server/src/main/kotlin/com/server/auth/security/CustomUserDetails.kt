package com.server.auth.security

import com.objects.shared.dto.PvcUserInfoDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val pvcUserInfoDto: PvcUserInfoDto
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf()
    override fun getPassword(): String = "{noop}${pvcUserInfoDto.password}"
    override fun getUsername(): String = pvcUserInfoDto.username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    val dbid: String = pvcUserInfoDto.id
}