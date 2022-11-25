package com.example.starwars.planet.service.out

import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PlanetClient {
    fun getAll(): Flux<Planet>
    fun getByName(name: String): Flux<Planet>
    fun getById(id: String): Mono<Planet>
    fun insertFromCommand(command: InsertPlanetCommand): Mono<Planet>
    fun removeById(id: String): Mono<Unit>
}
