package com.server.auth.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthorizationController {

    @GetMapping("/login")
    fun getLogin(): String {
        return "login"
    }
}