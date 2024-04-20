package com.storage.data.exception

import java.io.IOException

class LoadPvcUserNotFoundException(
    override val message: String? = null
) : IOException(message)