package com.api.converter.service

interface ConverterService {

    fun convert(data: ByteArray): ByteArray
}