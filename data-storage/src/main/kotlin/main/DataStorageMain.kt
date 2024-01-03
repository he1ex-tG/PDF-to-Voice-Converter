package main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataStorageMain

fun main(array: Array<String>) {
    runApplication<DataStorageMain>(*array)
}