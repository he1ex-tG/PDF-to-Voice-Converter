package com.storage.data.exception

import java.io.IOException

class SavePvcUserDuplicateException(
    override val message: String? = null
) : IOException(message)