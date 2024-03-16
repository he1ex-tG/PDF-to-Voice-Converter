package com.iface.user.model

import com.iface.user.validation.MultipartFileConstraint
import org.springframework.web.multipart.MultipartFile

class PvcIncomeData {
    @field:MultipartFileConstraint
    var file: MultipartFile? = null
}