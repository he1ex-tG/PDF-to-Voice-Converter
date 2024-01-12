package com.iface.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class UserInterfaceMain

fun main(array: Array<String>) {
    runApplication<UserInterfaceMain>(*array)
}