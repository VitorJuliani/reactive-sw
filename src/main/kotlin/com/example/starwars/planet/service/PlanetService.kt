package com.example.starwars.planet.service

import com.example.starwars.planet.exception.NotFoundException
import com.example.starwars.planet.`in`.controller.request.PlanetRequest
import com.example.starwars.planet.out.PlanetClient
import com.example.starwars.planet.service.domain.Planet
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.UUID

@Service
class PlanetService(private val planetClient: PlanetClient) {

    fun getAllPlanets(): Flux<Planet> {
        return planetClient.getAllPlanets()
            .flatMap { planet: Planet ->
                planetClient.getPlanetAppearances(planet.name)
                    .map {
                        Planet(
                            planet.name,
                            planet.climate,
                            planet.terrain,
                            it
                        )
                    }
            }
    }

    fun getPlanetByName(name: String): Mono<Planet> {
        val planets = planetClient.getPlanetByName(name)
            .switchIfEmpty { Mono.error(NotFoundException("Could not retrieve planet with name $name")) }

        val appearances = planets.flatMap { planetClient.getPlanetAppearances(it.name) }

        return Mono.zip(planets, appearances) { planet, appearance ->
            Planet(
                planet.name,
                planet.climate,
                planet.terrain,
                appearance
            )
        }
    }

    fun getPlanetById(id: String): Mono<Planet> {
        val planets = planetClient.getPlanetById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Could not retrieve planet with id $id")) }

        val appearances = planets.flatMap { planetClient.getPlanetAppearances(it.name) }

        return Mono.zip(planets, appearances) { planet, appearance ->
            Planet(
                planet.name,
                planet.climate,
                planet.terrain,
                appearance
            )
        }
    }

    fun savePlanet(planetRequest: PlanetRequest): Mono<UUID> {
        return planetClient.savePlanet(planetRequest)
    }

    fun removePlanet(id: String): Mono<Void> {
        return planetClient.deletePlanet(id)
    }
}
