package main.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.web.multipart.MultipartFile

class MultipartFileValidator : ConstraintValidator<MultipartFileConstraint, MultipartFile> {
    override fun isValid(p0: MultipartFile?, p1: ConstraintValidatorContext?): Boolean {
        return (p0 != null) && (p0.size > 0)
    }
}