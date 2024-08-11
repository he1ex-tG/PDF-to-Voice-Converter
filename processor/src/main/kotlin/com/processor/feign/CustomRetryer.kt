package com.processor.feign

import com.objects.shared.exception.PvcServiceException
import feign.RetryableException
import feign.Retryer

class CustomRetryer(
    private val maxAttempts: Int,
    private val backoff: Long,
    private val pvcServiceException: PvcServiceException
) : Retryer {

    private var attempt = 0

    override fun clone(): Retryer {
        return CustomRetryer(maxAttempts, backoff, pvcServiceException)
    }

    override fun continueOrPropagate(ex: RetryableException) {
        if (++attempt > maxAttempts) {
            throw pvcServiceException
        }
        try {
            Thread.sleep(backoff)
        }
        catch (ignored: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}