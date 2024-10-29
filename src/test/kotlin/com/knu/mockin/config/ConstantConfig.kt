package com.knu.mockin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class ConstantConfig {
    var user: UserConfig = UserConfig()

    class UserConfig {
        lateinit var email: String
    }
}