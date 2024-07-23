package com.server.auth.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail

fun Response.getProblemDetail(): ProblemDetail {
    val problemDetail = if (this.body() == null) {
        ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(this.status()), this.reason())
    } else {
        this.body().asInputStream().use {
            val mapper = ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            mapper.readValue(it, ProblemDetail::class.java)
        }
    }
    return problemDetail
}