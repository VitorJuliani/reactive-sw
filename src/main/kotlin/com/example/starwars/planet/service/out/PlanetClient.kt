package com.example.starwars.planet.service.out

import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PlanetClient : GetPlanet, InsertPlanet, RemovePlanet

interface GetPlanet {
    fun all(): Flux<Planet>
    fun byName(name: String): Flux<Planet>
    fun byId(id: String): Mono<Planet>
}

interface InsertPlanet {
    fun fromCommand(command: InsertPlanetCommand): Mono<Planet>
}

interface RemovePlanet {
    fun removeById(id: String): Mono<Unit>
}
