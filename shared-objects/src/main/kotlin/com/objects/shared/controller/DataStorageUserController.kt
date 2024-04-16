package com.objects.shared.controller

import com.objects.shared.dto.PvcUserDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface DataStorageUserController {

    @GetMapping(path = ["\${pvc.dataStorage.usersEndpoint}/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun loadPvcUser(@PathVariable("username") username: String): PvcUserDto

    @PostMapping(
        path = ["\${pvc.dataStorage.usersEndpoint}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun savePvcUser(@Valid @RequestBody pvcUserDto: PvcUserDto): PvcUserDto
}