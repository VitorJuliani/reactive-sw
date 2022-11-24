package com.example.starwars.planet.service.domain

data class Planet(
    val id: String = "",
    val name: String = "",
    val climate: String = "",
    val terrain: String = "",
    val filmAppearances: List<String> = emptyList()
)
