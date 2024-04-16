package com.storage.data.exception

import java.io.IOException

class LoadPvcUserException(
    override val message: String? = null
) : IOException(message)