package com.example.starwars.planet.out

import com.example.starwars.planet.`in`.controller.request.PlanetRequest
import com.example.starwars.planet.out.api.SWApiResponse
import com.example.starwars.planet.out.repository.PlanetDocument
import com.example.starwars.planet.out.repository.PlanetRepository
import com.example.starwars.planet.service.domain.Planet
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class PlanetClient(
    private val planetRepository: PlanetRepository,
    private val webClient: WebClient
) {

    fun getAllPlanets(): Flux<Planet> {
        return planetRepository.findAll().map { it.toDomain() }
    }

    fun getPlanetByName(name: String): Mono<Planet> {
        return planetRepository.findByName(name).map { it.toDomain() }
    }

    fun getPlanetById(id: String): Mono<Planet> {
        return planetRepository.findById(id).map { it.toDomain() }
    }

    fun savePlanet(planet: PlanetRequest): Mono<UUID> {
        return planetRepository.save(
            PlanetDocument(
                name = planet.name,
                climate = planet.climate,
                terrain = planet.terrain
            )
        ).map { UUID.fromString(it.id) }
    }

    fun deletePlanet(id: String): Mono<Void> {
        return planetRepository.deleteById(id)
    }

    fun getPlanetAppearances(name: String): Mono<List<String>> {
        return webClient
            .get()
            .uri { it.path("/planets").queryParam("search", name).build() }
            .retrieve()
            .bodyToMono(SWApiResponse::class.java)
            .map {
                if (it.results.isEmpty()) emptyList() else it.results.first().films
            }
    }
}
