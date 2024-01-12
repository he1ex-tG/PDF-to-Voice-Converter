package com.iface.user.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [MultipartFileValidator::class])
@Target(AnnotationTarget.FIELD)
annotation class MultipartFileConstraint(
    val message: String = "Invalid MultipartFile",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)