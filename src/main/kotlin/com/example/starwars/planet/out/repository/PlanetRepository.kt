package com.example.starwars.planet.out.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PlanetRepository : ReactiveMongoRepository<PlanetDocument, String> {
    fun findByName(name: String): Mono<PlanetDocument>
}
