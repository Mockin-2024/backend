package com.knu.mockin.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun webClientMock(): WebClient{
        return WebClient
            .builder()
            .baseUrl("https://openapivts.koreainvestment.com:29443")
            .build()
    }

    @Bean
    fun webClientReal(): WebClient{
        return WebClient
            .builder()
            .baseUrl("https://openapi.koreainvestment.com:9443")
            .build()
    }
}