package com.example.starwars.planet.out

import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.CacheClient
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RedisPlanetClient(
    private val redisTemplate: ReactiveRedisTemplate<String, Planet>
) : CacheClient {

    override fun removeByKey(key: String): Mono<Unit> {
        return redisTemplate.opsForValue().delete(key)
            .then(Mono.just(Unit))
    }

    override fun getByKey(key: String): Mono<Planet> {
        return redisTemplate.opsForValue()[key]
    }

    override fun insertPlanet(key: String, planet: Planet): Mono<Boolean> {
        return redisTemplate.opsForValue().set(key, planet)
    }
}
