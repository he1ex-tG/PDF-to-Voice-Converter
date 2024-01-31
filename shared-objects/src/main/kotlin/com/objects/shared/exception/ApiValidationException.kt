package com.objects.shared.exception

class ApiValidationException(
    val `object`: String,
    val message: String,
    val field: String,
    val rejectedValue: Any?
)