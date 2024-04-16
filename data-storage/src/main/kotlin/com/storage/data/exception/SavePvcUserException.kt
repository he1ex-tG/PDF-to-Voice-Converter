package com.storage.data.exception

import java.io.IOException

class SavePvcUserException(
    override val message: String? = null
) : IOException(message)