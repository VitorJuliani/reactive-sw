package com.example.starwars.planet.service

import com.example.starwars.planet.service.`in`.PlanetService
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.CacheClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.switchIfEmptyDeferred

class CachedPlanetService(
    private val delegate: PlanetService,
    private val cacheClient: CacheClient
) : PlanetService {

    override fun getAllPlanets(): Flux<Planet> {
        return cacheClient.getAllPlanets()
            .switchIfEmptyDeferred {
                delegate.getAllPlanets()
                    .flatMap { insertPlanet(it).thenReturn(it) }
            }
    }

    override fun getPlanetsByName(name: String): Flux<Planet> {
        return cacheClient.getPlanetsByName(name)
            .switchIfEmptyDeferred {
                delegate.getPlanetsByName(name)
                    .flatMap { insertPlanet(it).thenReturn(it) }
            }
    }

    override fun getPlanetById(id: String): Mono<Planet> {
        return cacheClient.getPlanetById(id)
            .switchIfEmpty {
                delegate.getPlanetById(id)
                    .flatMap { insertPlanet(it).thenReturn(it) }
            }
    }

    private fun insertPlanet(planet: Planet): Mono<Boolean> {
        return cacheClient.insertPlanet("${planet.id}-${planet.name}", planet)
    }

    override fun savePlanet(command: InsertPlanetCommand): Mono<Planet> {
        return cacheClient.removePlanets()
            .then(Mono.just(command))
            .flatMap(delegate::savePlanet)
    }

    override fun removePlanet(id: String): Mono<Unit> {
        return cacheClient.removePlanets()
            .then(Mono.just(id))
            .flatMap(delegate::removePlanet)
    }
}
