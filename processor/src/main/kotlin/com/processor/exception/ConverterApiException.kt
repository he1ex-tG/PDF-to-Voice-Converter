package com.processor.exception

import java.io.IOException

class ConverterApiException(
    val status: Int? = null,
    override val message: String? = null
) : IOException(message)