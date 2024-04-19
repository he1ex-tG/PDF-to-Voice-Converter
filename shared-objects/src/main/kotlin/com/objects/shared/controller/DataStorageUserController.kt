package com.objects.shared.controller

import com.objects.shared.dto.PvcUserDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface DataStorageUserController {

    @GetMapping(path = ["/auth/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authPvcUser(@PathVariable("username") username: String): PvcUserDto

    @PostMapping(
        path = ["/users"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun savePvcUser(@Valid @RequestBody pvcUserDto: PvcUserDto): PvcUserDto
}