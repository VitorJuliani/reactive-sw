package com.example.starwars.planet.service

import com.example.starwars.planet.service.`in`.PlanetService
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class DefaultPlanetService(
    private val planetClient: PlanetClient,
    private val filmAppearances: PlanetAppearances
) : PlanetService {

    override fun getAllPlanets(): Flux<Planet> {
        return planetClient.getAll()
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun getPlanetsByName(name: String): Flux<Planet> {
        return planetClient.getByName(name)
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun getPlanetById(id: String): Mono<Planet> {
        return planetClient.getById(id)
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun insertPlanet(command: InsertPlanetCommand): Mono<Planet> {
        return planetClient.insertFromCommand(command)
    }

    override fun removePlanet(id: String): Mono<Unit> {
        return planetClient.removeById(id)
    }
}
