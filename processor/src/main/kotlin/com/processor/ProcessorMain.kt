package com.processor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ProcessorMain

fun main(array: Array<String>) {
    runApplication<ProcessorMain>(*array)
}