package com.iface.user.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SystemController {

    @GetMapping("/user/logout")
    fun getLogout(): String {
        return "logout"
    }
}
