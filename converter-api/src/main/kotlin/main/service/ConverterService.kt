package main.service

interface ConverterService {

    fun convert(data: ByteArray): ByteArray
}