package com.processor.exception

class ConverterApiNotAvailable(
    override val message: String? = null
) : RuntimeException(message)