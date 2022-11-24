package com.example.starwars.planet.out

import com.example.starwars.planet.out.api.SWApiResponse
import com.example.starwars.planet.service.domain.Planet
import com.example.starwars.planet.service.out.PlanetAppearances
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WebClientPlanetClient(
    private val webClient: WebClient
): PlanetAppearances {

    override fun planetAppearances(planet: Planet): Mono<List<String>> {
        return webClient
            .get()
            .uri {
                it.path("/planets").queryParam("search", planet.name).build()
            }
            .retrieve()
            .bodyToMono(SWApiResponse::class.java)
            .map {
                if (it.results.isEmpty()) emptyList() else it.results[0].films
            }
    }
}
