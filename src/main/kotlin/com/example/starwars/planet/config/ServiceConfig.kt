package com.example.starwars.planet.config

import com.example.starwars.planet.service.CachedPlanetService
import com.example.starwars.planet.service.DefaultPlanetService
import com.example.starwars.planet.service.`in`.PlanetService
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
    fun cachedPlanetService(
        @Qualifier("defaultPlanetService") planetUseCases: PlanetService,
        cacheClient: CacheClient
        ): PlanetService {
        return CachedPlanetService(planetUseCases, cacheClient)
    }

    @Bean
    @Qualifier("defaultPlanetService")
    fun defaultPlanetService(
        planetClient: PlanetClient,
        filmAppearances: PlanetAppearances,
    ): PlanetService {
        return DefaultPlanetService(planetClient, filmAppearances)
    }
}
