package com.server.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AuthServerMain

fun main(array: Array<String>) {
    runApplication<AuthServerMain>(*array)
}