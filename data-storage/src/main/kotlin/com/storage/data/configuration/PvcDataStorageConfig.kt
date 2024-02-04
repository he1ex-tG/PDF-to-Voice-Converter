package com.storage.data.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pvc.dataStorage")
class PvcDataStorageConfig {
    lateinit var localStoragePath: String
}