package com.storage.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "PVC_user")
class PvcUser(
    @Id
    var id: String? = null,
    var username: String,
    var password: String
)