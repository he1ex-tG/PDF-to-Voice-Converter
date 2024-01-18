package com.processor.exception

import java.io.IOException

class ConverterApiException(
    override val message: String?
) : IOException()