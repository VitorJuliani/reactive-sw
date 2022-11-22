package com.example.starwars.planet.out.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SWApiResponse(
    val results: List<Planet>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Planet(
    val films: List<String>
)
