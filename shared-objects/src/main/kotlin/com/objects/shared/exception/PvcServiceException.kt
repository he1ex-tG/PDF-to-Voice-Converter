package com.objects.shared.exception

import java.lang.Exception

open class PvcServiceException(
    open val status: Int?,
    override val message: String?
) : Exception(message)