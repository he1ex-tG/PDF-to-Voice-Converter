package com.processor.exception

import com.objects.shared.exception.PvcServiceException

class DataStorageException(
    override val status: Int? = null,
    override val message: String? = null
) : PvcServiceException(status, message)