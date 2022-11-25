package com.example.starwars.planet.service.out

import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CacheClient {
    fun getAllPlanets(): Flux<Planet>
    fun getPlanetsByName(name: String): Flux<Planet>
    fun getPlanetById(id: String): Mono<Planet>
    fun insertPlanet(key: String, planet: Planet): Mono<Boolean>
    fun removePlanets(): Mono<Unit>
}
