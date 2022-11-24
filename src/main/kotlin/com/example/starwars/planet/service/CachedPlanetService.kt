package com.example.starwars.planet.service

import com.example.starwars.planet.service.`in`.PlanetUseCases
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.CacheClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.switchIfEmptyDeferred

class CachedPlanetService(
    private val delegate: PlanetUseCases,
    private val cacheClient: CacheClient
) : PlanetUseCases {

    override fun getAllPlanets(): Flux<Planet> {
        return cacheClient.getByKey("all")
            .flux()
            .switchIfEmptyDeferred {
                delegate.getAllPlanets()
                    .flatMap {
                        cacheClient.insertPlanet("all", it)
                            .thenReturn(it)
                    }
            }
    }

    override fun getPlanetsByName(name: String): Flux<Planet> {
        return cacheClient.getByKey(name)
            .flux()
            .switchIfEmptyDeferred {
                delegate.getPlanetsByName(name)
                    .flatMap {
                        cacheClient.insertPlanet("all", it)
                            .thenReturn(it)
                    }
            }
    }

    override fun getPlanetById(id: String): Mono<Planet> {
        return cacheClient.getByKey(id)
            .switchIfEmpty {
                delegate.getPlanetById(id)
                    .flatMap {
                        cacheClient.insertPlanet("all", it)
                            .thenReturn(it)
                    }
            }
    }

    override fun savePlanet(command: InsertPlanetCommand): Mono<Planet> {
        return cacheClient.removeByKey("all")
            .flatMap { delegate.savePlanet(command) }
    }

    override fun removePlanet(id: String): Mono<Unit> {
        return cacheClient.removeByKey("all")
            .flatMap { delegate.removePlanet(id) }
    }
}
