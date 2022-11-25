package com.example.starwars.planet.out

import com.example.starwars.planet.out.repository.PlanetDocument
import com.example.starwars.planet.out.repository.PlanetRepository
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.PlanetClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MongoPlanetClient(
    private val planetRepository: PlanetRepository,
    private val webClient: WebClient
) : PlanetClient {

    override fun getAll(): Flux<Planet> {
        return planetRepository.findAll()
            .map(PlanetDocument::toDomain)
    }

    override fun getByName(name: String): Flux<Planet> {
        return planetRepository.findAllByName(name)
            .map(PlanetDocument::toDomain)
    }

    override fun getById(id: String): Mono<Planet> {
        return planetRepository.findById(id)
            .map(PlanetDocument::toDomain)
    }

    override fun insertFromCommand(command: InsertPlanetCommand): Mono<Planet> {
        return planetRepository.save(
            PlanetDocument(name = command.name, climate = command.climate, terrain = command.terrain)
        )
            .map(PlanetDocument::toDomain)
    }

    override fun removeById(id: String): Mono<Unit> {
        return planetRepository.deleteById(id)
            .then(Mono.just(Unit))
    }
}
