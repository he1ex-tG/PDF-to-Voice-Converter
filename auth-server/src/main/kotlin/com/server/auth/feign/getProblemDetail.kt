package com.server.auth.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import org.springframework.http.ProblemDetail

fun Response.getProblemDetail(): ProblemDetail {
    return this.body().asInputStream().use {
        val mapper = ObjectMapper()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        mapper.readValue(it, ProblemDetail::class.java)
    }
}