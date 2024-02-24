package com.processor.exception

class DataStorageNotAvailable(
    override val message: String? = null
) : RuntimeException(message)