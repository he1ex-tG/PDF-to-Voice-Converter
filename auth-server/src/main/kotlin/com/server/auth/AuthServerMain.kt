package com.server.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServerMain

fun main(array: Array<String>) {
    runApplication<AuthServerMain>(*array)
}