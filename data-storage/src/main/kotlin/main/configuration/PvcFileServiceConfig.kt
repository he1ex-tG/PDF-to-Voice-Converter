package main.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pvc.file")
class PvcFileServiceConfig {
    lateinit var localStoragePath: String
}