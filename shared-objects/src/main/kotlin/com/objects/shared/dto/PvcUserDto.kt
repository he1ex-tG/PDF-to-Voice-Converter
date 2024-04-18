package com.objects.shared.dto

import jakarta.validation.constraints.NotEmpty

open class PvcUserDto(
    @field:NotEmpty(message = "{dataStorage.pvcUserConstrainedDto.username.isEmpty}")
    open val username: String,
    @field:NotEmpty(message = "{dataStorage.pvcUserConstrainedDto.password.isEmpty}")
    open val password: String,
    @field:NotEmpty(message = "{dataStorage.pvcUserConstrainedDto.email.isEmpty}")
    open val email: String
)