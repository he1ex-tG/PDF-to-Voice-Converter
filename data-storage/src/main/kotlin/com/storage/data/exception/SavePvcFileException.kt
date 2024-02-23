package com.storage.data.exception

import java.io.IOException

class SavePvcFileException(
    override val message: String? = null
) : IOException(message)