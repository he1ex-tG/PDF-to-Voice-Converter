package com.storage.data.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pvc.data-storage")
class PvcDataStorageConfig {
    lateinit var localStoragePath: String
    lateinit var localStorageSize: String
}