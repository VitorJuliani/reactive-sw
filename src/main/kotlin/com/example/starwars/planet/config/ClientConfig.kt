package com.example.starwars.planet.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfig {

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl("https://swapi.dev/api")
            .build()
    }
}
