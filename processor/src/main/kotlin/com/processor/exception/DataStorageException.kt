package com.processor.exception

import java.io.IOException

class DataStorageException(
    val status: Int? = null,
    override val message: String? = null
) : IOException(message)