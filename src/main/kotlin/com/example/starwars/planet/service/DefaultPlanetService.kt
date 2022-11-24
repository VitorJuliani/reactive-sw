package com.example.starwars.planet.service

import com.example.starwars.planet.service.`in`.PlanetUseCases
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class DefaultPlanetService(
    private val planetClient: PlanetClient,
    private val filmAppearances: PlanetAppearances
) : PlanetUseCases {

    override fun getAllPlanets(): Flux<Planet> {
        return planetClient.all()
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun getPlanetsByName(name: String): Flux<Planet> {
        return planetClient.byName(name)
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun getPlanetById(id: String): Mono<Planet> {
        return planetClient.byId(id)
            .flatMap { planet ->
                filmAppearances.planetAppearances(planet)
                    .map { planet.copy(filmAppearances = it) }
            }
    }

    override fun savePlanet(command: InsertPlanetCommand): Mono<Planet> {
        return planetClient.fromCommand(command)
    }

    override fun removePlanet(id: String): Mono<Unit> {
        return planetClient.removeById(id)
    }
}
