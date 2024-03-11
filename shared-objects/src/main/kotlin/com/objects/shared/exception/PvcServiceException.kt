package com.objects.shared.exception

open class PvcServiceException(
    open val status: Int?,
    override val message: String?
) : RuntimeException(message)