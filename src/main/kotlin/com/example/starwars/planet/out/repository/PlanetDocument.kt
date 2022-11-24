package com.example.starwars.planet.out.repository

import com.example.starwars.planet.service.domain.Planet
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(value = "planet")
data class PlanetDocument(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val climate: String,
    val terrain: String
) {
    fun toDomain(): Planet {
        return Planet(id = this.id, name = this.name, climate = this.climate, terrain = this.terrain)
    }
}
