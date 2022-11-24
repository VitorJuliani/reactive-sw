package com.example.starwars.planet.config

import com.example.starwars.planet.service.domain.Planet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CacheConfig {

    @Bean
    fun redis(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Planet> {
        val context = RedisSerializationContext
            .newSerializationContext<String, Planet>(StringRedisSerializer())
            .value(Jackson2JsonRedisSerializer(Planet::class.java))
            .build()

        return ReactiveRedisTemplate(factory, context)
    }
}
