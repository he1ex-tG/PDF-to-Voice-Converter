package com.api.converter.exception

import java.io.IOException

class TtsEmptyStringException(
    override val message: String? = null
) : IOException(message)