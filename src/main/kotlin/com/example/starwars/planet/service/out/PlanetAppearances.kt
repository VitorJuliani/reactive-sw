package com.example.starwars.planet.service.out

import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Mono

interface PlanetAppearances {
    fun planetAppearances(planet: Planet): Mono<List<String>>
}
