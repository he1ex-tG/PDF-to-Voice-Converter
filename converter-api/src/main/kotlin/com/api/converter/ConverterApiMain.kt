package com.api.converter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConverterApiMain

fun main(array: Array<String>) {
    runApplication<ConverterApiMain>(*array)
}