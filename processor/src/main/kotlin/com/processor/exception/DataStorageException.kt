package com.processor.exception

import java.io.IOException

class DataStorageException(
    override val message: String?
) : IOException()