package com.example.starwars.planet.config

import com.example.starwars.planet.service.CachedPlanetService
import com.example.starwars.planet.service.DefaultPlanetService
import com.example.starwars.planet.service.`in`.PlanetUseCases
import com.example.starwars.planet.service.out.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class ServiceConfig {

    @Bean
    @Qualifier("cachedPlanetService")
    @Primary
    fun planetService(
        @Qualifier("defaultPlanetService") planetUseCases: PlanetUseCases,
        cacheClient: CacheClient
        ): PlanetUseCases {
        return CachedPlanetService(planetUseCases, cacheClient)
    }

    @Bean
    @Qualifier("defaultPlanetService")
    fun planetService(
        planetClient: PlanetClient,
        filmAppearances: PlanetAppearances,
    ): PlanetUseCases {
        return DefaultPlanetService(planetClient, filmAppearances)
    }
}
