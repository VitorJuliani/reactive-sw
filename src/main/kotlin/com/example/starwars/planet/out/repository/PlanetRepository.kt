package com.example.starwars.planet.out.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PlanetRepository : ReactiveMongoRepository<PlanetDocument, String> {
    fun findAllByName(name: String): Flux<PlanetDocument>
}
