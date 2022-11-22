package com.example.starwars.planet.service.domain

data class Planet(
    val name: String,
    val climate: String,
    val terrain: String,
    val films: List<String> = emptyList()
)
