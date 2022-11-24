package com.example.starwars.planet.service.`in`.command

data class InsertPlanetCommand(
    val name: String,
    val climate: String,
    val terrain: String
)
