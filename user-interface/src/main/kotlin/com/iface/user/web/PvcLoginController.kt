package com.iface.user.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PvcLoginController {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}