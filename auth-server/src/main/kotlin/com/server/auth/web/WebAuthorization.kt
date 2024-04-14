package com.server.auth.web

import com.server.auth.model.UserRegistrationData
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WebAuthorization {

    @GetMapping("/login")
    fun getLogin(): String {
        return "login"
    }

    @GetMapping("/registration")
    fun getRegistration(model: Model): String {
        model.addAttribute("registrationData", UserRegistrationData())
        return "registration"
    }

    @PostMapping("/registration")
    fun postRegistration(
        @Valid
        @ModelAttribute("registrationData")
        userRegistrationData: UserRegistrationData,
        errors: Errors
    ): String {
        if (errors.hasErrors()) {
            return "registration"
        }
        println(userRegistrationData.email)
        return "login"
    }
}