package com.knu.mockin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun webClient(): WebClient{
        return WebClient
            .builder()
            .baseUrl("https://openapivts.koreainvestment.com:29443")
            .build()
    }
}