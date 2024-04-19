package com.server.auth.model

import com.objects.shared.dto.PvcUserDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UserRegistrationData(
    @field:NotEmpty(message = "{registration.username.required}")
    @field:Size(min = 2, max = 21, message = "{registration.username.size}")
    @field:Pattern(regexp = "^[0-9A-Za-z_-]+$", message = "{registration.username.pattern}")
    var username: String = "",
    @field:NotEmpty(message = "{registration.email.required}")
    @field:Email(message = "{registration.email.pattern}")
    @field:Size(max = 30, message = "{registration.email.max}")
    var email: String = "",
    @field:NotEmpty(message = "{registration.password.required}")
    @field:Size(min = 5, max = 24, message = "{registration.password.size}")
    var password: String = ""
) {

    fun toPvcUserDto(): PvcUserDto =
        PvcUserDto(username, password, email)
}