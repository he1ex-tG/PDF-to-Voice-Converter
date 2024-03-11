package com.iface.user.exception

import com.objects.shared.exception.PvcServiceException

class ProcessorException(
    override val status: Int? = null,
    override val message: String? = null
) : PvcServiceException(status, message)