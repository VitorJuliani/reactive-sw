package com.example.starwars.planet.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import java.time.Instant

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notFoundExceptionHandler(ex: NotFoundException): Mono<ErrorResponse> {
        return Mono.just(ErrorResponse(message = ex.message))
    }

    data class ErrorResponse(
        val message: String,
        val timestamp: Instant = Instant.now()
    )
}

data class NotFoundException(override val message: String) : RuntimeException(message)
