package com.iface.user.exception

import java.io.IOException

class ProcessorException(
    override val message: String?
) : IOException()