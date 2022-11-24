package com.example.starwars.planet.service.out

import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Mono

interface CacheClient {
    fun getByKey(key: String): Mono<Planet>
    fun insertPlanet(key: String, planet: Planet): Mono<Boolean>
    fun removeByKey(key: String): Mono<Unit>
}
