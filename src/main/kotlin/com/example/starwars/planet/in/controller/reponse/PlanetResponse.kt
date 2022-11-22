package com.example.starwars.planet.`in`.controller.reponse

import com.example.starwars.planet.service.domain.Planet

data class PlanetResponse(
    val name: String,
    val climate: String,
    val terrain: String,
    val filmAppearances: Int
) {
    companion object {
        fun buildFromPlanet(planet: Planet) = PlanetResponse(
            name = planet.name,
            climate = planet.climate,
            terrain = planet.terrain,
            filmAppearances = planet.films.size
        )
    }
}
