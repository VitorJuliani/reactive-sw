package com.example.starwars.planet.service.`in`

import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PlanetService {
    fun getAllPlanets(): Flux<Planet>
    fun getPlanetsByName(name: String): Flux<Planet>
    fun getPlanetById(id: String): Mono<Planet>
    fun savePlanet(command: InsertPlanetCommand): Mono<Planet>
    fun removePlanet(id: String): Mono<Unit>
}
