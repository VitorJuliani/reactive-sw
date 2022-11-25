package com.example.starwars.planet.out

import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.CacheClient
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class RedisPlanetClient(
    private val redisTemplate: ReactiveRedisTemplate<String, Planet>
) : CacheClient {

    override fun getAllPlanets(): Flux<Planet> {
        return redisTemplate.keys("*")
            .flatMap(redisTemplate.opsForValue()::get)
    }

    override fun getPlanetsByName(name: String): Flux<Planet> {
        return redisTemplate.keys("*$name")
            .flatMap(redisTemplate.opsForValue()::get)
    }

    override fun getPlanetById(id: String): Mono<Planet> {
        return redisTemplate.keys("$id*")
            .flatMap(redisTemplate.opsForValue()::get)
            .toMono()
    }

    override fun insertPlanet(key: String, planet: Planet): Mono<Boolean> {
        return redisTemplate.opsForValue().set(key, planet)
    }

    override fun removePlanets(): Mono<Unit> {
        return redisTemplate.keys("*")
            .flatMap { redisTemplate.opsForValue().delete(it) }
            .then(Mono.just(Unit))
    }
}
