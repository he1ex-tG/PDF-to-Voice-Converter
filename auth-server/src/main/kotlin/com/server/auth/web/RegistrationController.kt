package com.server.auth.web

import com.server.auth.model.UserRegistrationData
import com.server.auth.service.PvcUserService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class RegistrationController(
    private val pvcUserService: PvcUserService
) {

    @ModelAttribute("registrationData")
    fun setRegistrationData(): UserRegistrationData {
        return UserRegistrationData()
    }

    @GetMapping("/registration")
    fun getRegistration(model: Model): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun postRegistration(
        @Valid @ModelAttribute("registrationData") userRegistrationData: UserRegistrationData,
        errors: Errors,
        redirectAttributes: RedirectAttributes
    ): String {
        if (errors.hasErrors()) {
            return "registration"
        }
        val saveResult = pvcUserService.savePvcUser(userRegistrationData.toPvcUserDto())
        redirectAttributes.addFlashAttribute("newUsername", saveResult.username)
        return "redirect:login"
    }
}