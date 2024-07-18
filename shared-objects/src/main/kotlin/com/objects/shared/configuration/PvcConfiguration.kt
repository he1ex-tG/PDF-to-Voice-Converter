package com.objects.shared.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "pvc")
class PvcConfiguration {

    lateinit var processor: FieldProcessor
    lateinit var userInterface: FieldUserInterface
    lateinit var authServer: FieldAuthServer
}