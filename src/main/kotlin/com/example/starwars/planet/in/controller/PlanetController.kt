package com.example.starwars.planet.`in`.controller

import com.example.starwars.planet.`in`.controller.reponse.PlanetResponse
import com.example.starwars.planet.`in`.controller.request.PlanetRequest
import com.example.starwars.planet.service.`in`.PlanetUseCases
import com.example.starwars.planet.service.`in`.command.InsertPlanetCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/api/v1/planets")
class PlanetController(private val planetOperations: PlanetUseCases) {

    @GetMapping
    fun getAllPlanets(): Flux<PlanetResponse> {
        return planetOperations.getAllPlanets()
            .map { PlanetResponse.buildFromPlanet(it) }
    }

    @GetMapping("/name/{planetName}")
    fun getPlanetByName(@PathVariable("planetName") name: String): Flux<PlanetResponse> {
        return planetOperations.getPlanetsByName(name)
            .map { PlanetResponse.buildFromPlanet(it) }
    }

    @GetMapping("/{planetId}")
    fun getPlanetById(@PathVariable("planetId") id: String): Mono<PlanetResponse> {
        return planetOperations.getPlanetById(id)
            .map { PlanetResponse.buildFromPlanet(it) }
    }

    @PostMapping
    fun savePlanet(@RequestBody planetRequest: PlanetRequest): Mono<ResponseEntity<Unit>> {
        return Mono.just(InsertPlanetCommand(planetRequest.name, planetRequest.climate, planetRequest.terrain))
            .flatMap { planetOperations.savePlanet(it) }
            .map { ResponseEntity.created(URI.create("/planets/${it.id}")).build() }
    }

    @DeleteMapping("/{planetId}")
    fun removePlanet(@PathVariable("planetId") id: String): Mono<ResponseEntity<Unit>> {
        return planetOperations.removePlanet(id)
            .then(Mono.just(ResponseEntity.noContent().build()))
    }
}
